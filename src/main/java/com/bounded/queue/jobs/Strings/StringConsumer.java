package com.bounded.queue.jobs.Strings;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringConsumer implements Runnable {

    private final BoundedQueue<String> sharedQueue;
    private final String name;
    private final String registeredObject;
    private final static Logger logger = Logger.getLogger(StringConsumer.class.getName());

    public StringConsumer(final BoundedQueue sharedQueue, final String name, final String registeredObject) {
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



