package com.bounded.queue.jobs.Integers;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegerConsumer implements Runnable {

    private final BoundedQueue<Integer> sharedQueue;
    private final String name;
    private final Integer registeredObject;
    private final static Logger logger = Logger.getLogger(IntegerConsumer.class.getName());

    public IntegerConsumer(final BoundedQueue sharedQueue, final String name, Integer registeredObject) {
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



