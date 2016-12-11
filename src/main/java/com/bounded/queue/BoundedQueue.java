package com.bounded.queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BoundedQueue<T> {

    private int capacity;
    private int currentSizeOfBuffer;
    private List<T> buffer;
    private final Object monitor = new Object();

    public void put(T element) throws InterruptedException {

        synchronized (monitor) {

            while(isBufferFull()) {
                waitOnAvailableSlot();
            }

            buffer.add(element);
            currentSizeOfBuffer++;

            informConsumerQueueHasElement();
        }
    }

    public T take(T interestedIn) throws InterruptedException {   //look into handling this in JCIP

        synchronized (monitor) {

            while(isBufferEmpty()) {
                waitOnAvailableElement();
            }

            T element = null;
            for(Iterator<T> iter = buffer.iterator(); iter.hasNext();) {
                T nextElement = iter.next();
                if (nextElement == interestedIn) {
                    element = nextElement;
                    iter.remove();
                    currentSizeOfBuffer--;
                }
            }

            informProducerQueueHasSpaceAvailable();

            return element;
        }
    }

    public boolean contains(T element) {
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
        monitor.wait();
    }

    private void informConsumerQueueHasElement() {
        monitor.notifyAll();
    }

    private boolean isBufferEmpty() {
        return 0 == currentSizeOfBuffer;
    }

    private void waitOnAvailableElement() throws InterruptedException {
        monitor.wait();
    }

    private void informProducerQueueHasSpaceAvailable() {
        monitor.notifyAll();
    }
}







//    private final int getNextAvailableSlot(int currentSlotPosition) {
//        int nextAvailableSlot = ++currentSlotPosition;
//        return (nextAvailableSlot == capacity) ? 0 : nextAvailableSlot;
//    }
//    public void registerInterest(T element, Runnable consumer) {
//        if(consumers.containsKey(element)) {
//            consumers.get(element).add(consumer);
//        } else {
//            consumers.put(element, new LinkedList<>(Arrays.asList(consumer)));
//        }
//    }
//
//    public T take() throws InterruptedException {   //look into handling this in JCIP
//
//        final ReentrantLock lock = this.lock;
//        lock.lock();
//
//        try {
//
//            while(isBufferEmpty()) {
//                waitOnAvailableElement();
//            }
//
//            T element = buffer[head];
//
//            if(element==null) {
//                int v = 0;
//            }
//
//            head = getNextAvailableSlot(head);
//            currentSizeOfBuffer--;
//
//            informProducerQueueHasSpaceAvailable();
//
//            return element;
//        } finally {
//            lock.unlock();
//        }
//    }

//    public void put(T element) throws InterruptedException {
//
//        final ReentrantLock lock = this.lock;
//        lock.lock();
//
//        try {
//
//            while(isBufferFull()) {
//                waitOnAvailableSlot();
//            }
//
//            buffer[tail] = element;
//            tail = getNextAvailableSlot(tail);
//            currentSizeOfBuffer++;
//
//            informConsumerQueueHasElement();
//
//        } finally {
//            lock.unlock();
//        }
//    }