package com.bounded.queue;

import java.util.LinkedList;

public class BoundedQueue {

    private int capacity;
    private int currentSizeOfBuffer;
    private LinkedList<Integer> buffer;
    private final Object bufferAccessLock = new Object();

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

    public Integer take(Integer element) throws InterruptedException {   //look into handling this in JCIP

        synchronized (bufferAccessLock) {

            while (isBufferEmpty()) {
                waitOnAvailableElement();
            }

            Integer foundElement = null;
            if(contains(element)) {
                foundElement = removeElement(element);
            }

            informProducerQueueHasSpaceAvailable();

            return foundElement;
        }
    }

    //is this right????
    private Integer removeElement(Integer element) {
        return buffer.removeFirstOccurrence(element) ? element : null;
    }

    public boolean contains(Object element) {
        return buffer.contains(element) ? true : false;
    }

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
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

    private boolean isBufferEmpty() {
        return 0 == currentSizeOfBuffer;
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
