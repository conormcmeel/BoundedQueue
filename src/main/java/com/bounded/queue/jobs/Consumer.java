package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer<T> implements Runnable {

    private final BoundedQueue<T> sharedQueue;
    private final String name;
    private final T registeredObject;
    private final static Logger logger = Logger.getLogger(Consumer.class.getName());

    public Consumer(final BoundedQueue sharedQueue, final String name, T registeredObject) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.registeredObject = registeredObject;
    }

    @Override
    public void run() {

        try {
            System.out.println(name + " consumed: " + sharedQueue.take(registeredObject));

        } catch (InterruptedException ex) {
            logException(ex);
        }
    }

    private void logException(InterruptedException ex) {
        logger.log(Level.SEVERE, name + "interrupted", ex);
        Thread.currentThread().interrupt();
    }
}



