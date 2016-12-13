package com.bounded.queue;

import java.util.LinkedList;

public class BoundedQueue {

    private int capacity;
    private int currentSizeOfBuffer;
    private LinkedList<Integer> buffer;
    private final Object bufferAccessLock = new Object();

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    public void put(Integer element) throws InterruptedException {

        synchronized (bufferAccessLock) {

            while (isBufferFull()) {
                waitOnAvailableSlot();
            }

            buffer.add(element);
            currentSizeOfBuffer++;

            informConsumerQueueHasElement();
        }
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

    public Integer take(Integer element) throws InterruptedException {

        synchronized (bufferAccessLock) {

            while (isBufferEmpty()) {
                waitOnAvailableElement();
            }

            Integer removedElement = null;
            if(contains(element)) {
                removedElement = removeElement(element);
            }

            informProducerQueueHasSpaceAvailable();

            return removedElement;
        }
    }

    private boolean isBufferEmpty() {
        return 0 == currentSizeOfBuffer;
    }

    private void waitOnAvailableElement() throws InterruptedException {
        bufferAccessLock.wait();
    }

    public boolean contains(Object element) {
        return buffer.contains(element) ? true : false;
    }

    //is this right????
    private Integer removeElement(Integer element) {
        return buffer.removeFirstOccurrence(element) ? element : null;
    }

    private void informProducerQueueHasSpaceAvailable() {
        bufferAccessLock.notifyAll();
    }

    public int getSize() {
        return buffer.size();
    }
}
