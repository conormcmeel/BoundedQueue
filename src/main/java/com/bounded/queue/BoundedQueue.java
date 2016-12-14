package com.bounded.queue;

import java.util.LinkedList;

public class BoundedQueue<T> {

    private int capacity;
    private int currentSizeOfBuffer;
    private LinkedList<T> buffer;
    private final Object bufferAccessLock = new Object();

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
    }

    public void put(T element) throws InterruptedException {

        synchronized (bufferAccessLock) {

            while (isBufferFull()) {
                waitOnAvailableSlot();
            }

            buffer.add(element);
            currentSizeOfBuffer++;

            informConsumerQueueHasElement();

//            for(int i=0; i< consumers.length; i++) {
//                synchronized (consumers[i]) {
//
//                    try {
//                        //Thread.sleep(500);
//                    } catch (Exception ex) {
//                        System.out.println(ex);
//                    }
//
//                    System.out.println("notifying" + consumers[i].name);
//                    consumers[i].notify();
//                }
//            }
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

    public T take(T element) throws InterruptedException {

        synchronized (bufferAccessLock) {

            while (isBufferEmpty()) {
                waitOnAvailableElement();
            }

            T removedElement = null;
            if(contains(element)) {
                removedElement = removeElement(element);
                currentSizeOfBuffer--;
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
