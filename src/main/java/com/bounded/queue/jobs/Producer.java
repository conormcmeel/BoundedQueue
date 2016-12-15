package com.bounded.queue.jobs;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Producer implements Runnable {

    private final static Logger logger = Logger.getLogger(Producer.class.getName());

    protected void logException(InterruptedException ex) {
        logger.log(Level.SEVERE, Thread.currentThread().getName() + "interrupted", ex);
        Thread.currentThread().interrupt();
    }
}
