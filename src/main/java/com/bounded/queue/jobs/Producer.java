package com.bounded.queue.jobs;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Producer implements Runnable {

    private final String name;
    private final static Logger logger = Logger.getLogger(Producer.class.getName());

    public Producer(final String name) {
        this.name = name;
    }

    protected void logException(InterruptedException ex) {
        logger.log(Level.SEVERE, name + "interrupted", ex);
        Thread.currentThread().interrupt();
    }
}
