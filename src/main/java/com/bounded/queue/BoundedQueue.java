package com.bounded.queue;

import java.util.*;
import java.util.logging.Logger;

public class BoundedQueue<T> {

    private final int capacity;
    private int currentSizeOfBuffer;
    private final LinkedList<T> buffer;
    private final Object bufferAccessLock = new Object();
    private final List<RegisteredConsumer> registeredConsumers;
    private final static Logger logger = Logger.getLogger(BoundedQueue.class.getName());

    private static class RegisteredConsumer<T> {
        volatile boolean consumerNotified = false;
        final T desiredElement;

        private RegisteredConsumer(T desiredElement) {
            this.desiredElement = desiredElement;
        }
    }

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
        this.registeredConsumers = new ArrayList<>();
    }

    public void put(T element) throws InterruptedException {

        synchronized (bufferAccessLock) {

            while (isBufferFull()) {
                waitOnAvailableSlot();
            }

            buffer.add(element);
            currentSizeOfBuffer++;

            Optional<RegisteredConsumer> consumer = findRegisteredConsumer(element);

            if (consumer.isPresent()) {
                RegisteredConsumer registeredConsumer = consumer.get();
                registeredConsumer.consumerNotified = true;
                registeredConsumers.remove(consumer);
            }

            logger.info(Thread.currentThread().getName() + " produced " + element);

            informConsumerQueueHasElement();
        }
    }

    private boolean isBufferFull() {
        return capacity == currentSizeOfBuffer;
    }

    private void waitOnAvailableSlot() throws InterruptedException {
        bufferAccessLock.wait();
    }

    private Optional<RegisteredConsumer> findRegisteredConsumer(T element) {
        return registeredConsumers.stream()
                .filter(c -> c.desiredElement.equals(element))
                .findFirst();
    }

    private void informConsumerQueueHasElement() {
        bufferAccessLock.notifyAll();
    }

    public T take(T element) throws InterruptedException {

        synchronized (bufferAccessLock) {

            if (contains(element) && !findRegisteredConsumer(element).isPresent()) {
                bufferAccessLock.notifyAll();
                return takeInternal(element);
            }

            RegisteredConsumer registeredConsumer = new RegisteredConsumer(element);
            registeredConsumers.add(registeredConsumer);

            while (!registeredConsumer.consumerNotified || !contains(element)) {
                logger.info(Thread.currentThread().getName() + " waiting on " + element);
                waitOnAvailableElement();
            }

            logger.info(Thread.currentThread().getName() + " consumed " + element);

            informProducerQueueHasSpaceAvailable();

            return takeInternal(element);
        }
    }

    private T takeInternal(T element) {
        currentSizeOfBuffer--;
        return removeElement(element);
    }

    private T removeElement(T element) {
        return buffer.removeFirstOccurrence(element) ? element : null;
    }

    public boolean contains(T element) {
        return buffer.contains(element) ? true : false;
    }

    private void waitOnAvailableElement() throws InterruptedException {
        bufferAccessLock.wait();
    }

    private void informProducerQueueHasSpaceAvailable() {
        bufferAccessLock.notifyAll();
    }

    public int getSize() {
        return buffer.size();
    }
}
