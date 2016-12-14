package com.bounded.queue;

import java.util.*;

public class BoundedQueue<T> {

    private int capacity;
    private int currentSizeOfBuffer;
    private LinkedList<T> buffer;
    private final Object bufferAccessLock = new Object();
    private List<RegisteredConsumer> registeredConsumers;

    private static class RegisteredConsumer {
        volatile boolean consumerNotified = false;
        final Object desiredElement;

        private RegisteredConsumer(Object desiredElement) {
            this.desiredElement = desiredElement;
        }
    }

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
        registeredConsumers = Collections.synchronizedList(new ArrayList<>());
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
            informConsumerQueueHasElement();
        }
    }

    private Optional<RegisteredConsumer> findRegisteredConsumer(T element) {
        return registeredConsumers.stream()
                .filter(c -> c.desiredElement.equals(element))
                .findFirst();
    }

    private boolean isBufferFull() {
        return capacity == currentSizeOfBuffer;
    }

    private void waitOnAvailableSlot() throws InterruptedException {
        bufferAccessLock.wait();
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

            while (!registeredConsumer.consumerNotified && !contains(element)) {
                waitOnAvailableElement();
            }

            bufferAccessLock.notifyAll();
            return takeInternal(element);
        }
    }

    private T takeInternal(T element) {
        T removedElement;
        removedElement = removeElement(element);
        currentSizeOfBuffer--;
        return removedElement;
    }

    private boolean isBufferEmpty() {
        return 0 == currentSizeOfBuffer;
    }

    private void waitOnAvailableElement() throws InterruptedException {
        bufferAccessLock.wait();
    }

    public boolean contains(T element) {
        return buffer.contains(element) ? true : false;
    }

    private T removeElement(T element) {
        return buffer.removeFirstOccurrence(element) ? element : null;
    }

    private void informProducerQueueHasSpaceAvailable() {
        bufferAccessLock.notifyAll();
    }

    public int getSize() {
        return buffer.size();
    }
}
