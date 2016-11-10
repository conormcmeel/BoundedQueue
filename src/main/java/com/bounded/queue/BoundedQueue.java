package com.bounded.queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> {

    private int capacity;
    private int head;
    private int tail;
    private int currentSizeOfBuffer;
    private T[] buffer;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.buffer = (T[]) new Object[capacity];
    }

    public void put(T element) throws InterruptedException {

        final ReentrantLock lock = this.lock;
        lock.lock();

        try {

            if(isBufferFull()) {
                waitOnAvailableSlot();
            }

            buffer[tail] = element;
            tail = getNextAvailableSlot(tail);
            currentSizeOfBuffer++;

            informConsumerQueueHasElement();

        } finally {
            lock.unlock();
        }
    }

    private boolean isBufferFull() {
        return capacity == currentSizeOfBuffer;
    }

    private void waitOnAvailableSlot() throws InterruptedException {
        notFull.await();
    }

    private void informConsumerQueueHasElement() {
        notEmpty.signal();
    }

    public T take() throws InterruptedException {   //loook into handling this in JCIP

        final ReentrantLock lock = this.lock;
        lock.lock();

        try {

            if(isBufferEmpty()) {
                waitOnAvailableElement();
            }

            T element = buffer[head];

            if(element==null) {
                int v = 0;
            }

            head = getNextAvailableSlot(head);
            currentSizeOfBuffer--;

            informProducerQueueHasSpaceAvailable();

            return element;
        } finally {
            lock.unlock();
        }
    }

    private boolean isBufferEmpty() {
        return 0 == currentSizeOfBuffer;
    }

    private void waitOnAvailableElement() throws InterruptedException {
        notEmpty.await();
    }

    private void informProducerQueueHasSpaceAvailable() {
        notFull.signalAll();
    }

    private final int getNextAvailableSlot(int currentSlotPosition) {
        int nextAvailableSlot = ++currentSlotPosition;
        return (nextAvailableSlot == capacity) ? 0 : nextAvailableSlot;
    }
}
