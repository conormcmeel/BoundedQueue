package com.bounded.queue.jobs.Strings;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringProducer implements Runnable {

    private final BoundedQueue<String> sharedQueue;
    private final String name;
    private final Object lock;
    private final static Logger logger = Logger.getLogger(StringProducer.class.getName());

    public StringProducer(final BoundedQueue sharedQueue, final String name, final Object lock) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.lock = lock;
    }

    @Override
    public void run() {

        synchronized (lock) {

            for(int i=1; i<=5; i++){

                try {

                    String value = "String" + i;
                    sharedQueue.put(value);
                    System.out.println(name + " produced: " + value);

                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, name + "interrupted", ex);
                    Thread.currentThread().interrupt();
                } finally {
                    lock.notifyAll();
                }
            }
        }
    }
}
