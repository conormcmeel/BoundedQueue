package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private final BoundedQueue sharedQueue;
    private final String name;
    private final Object lock;
    private final static Logger logger = Logger.getLogger(Consumer.class.getName());

    @Override
    public void run() {

        synchronized (lock) {

            for(int i=1; i<=5; i++){
                try {

                    sharedQueue.put(i);
                    System.out.println(name + " produced: " + i);

                } catch (InterruptedException ex) {
                    logger.log(Level.SEVERE, name + "interrupted", ex);
                    Thread.currentThread().interrupt();
                } finally {
                    lock.notifyAll();
                }
            }
        }
    }

    public Producer(final BoundedQueue sharedQueue, final String name, final Object lock) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.lock = lock;
    }
}
