package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer<T> implements Runnable {

    private final BoundedQueue<T> sharedQueue;
    private final T registeredObject;
    private final static Logger logger = Logger.getLogger(Consumer.class.getName());

    public Consumer(final BoundedQueue sharedQueue, T registeredObject) {
        this.sharedQueue = sharedQueue;
        this.registeredObject = registeredObject;
    }

    @Override
    public void run() {

        try {
            sharedQueue.take(registeredObject);

        } catch (InterruptedException ex) {
            logException(ex);
        }
    }

    private void logException(InterruptedException ex) {
        logger.log(Level.SEVERE, Thread.currentThread().getName() + "interrupted", ex);
        Thread.currentThread().interrupt();
    }
}



