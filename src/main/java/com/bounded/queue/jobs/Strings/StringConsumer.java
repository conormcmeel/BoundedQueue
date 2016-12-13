package com.bounded.queue.jobs.Strings;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StringConsumer implements Runnable {

    private final BoundedQueue<String> sharedQueue;
    private final String name;
    private final Object lock;
    private final String registeredObject;
    private final static Logger logger = Logger.getLogger(StringConsumer.class.getName());

    public StringConsumer(final BoundedQueue sharedQueue, final String name, final Object lock, final String registeredObject) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.lock = lock;
        this.registeredObject = registeredObject;
    }

    @Override
    public void run() {

        synchronized (lock) {

            try {

                while (!sharedQueue.contains(registeredObject)) {
                    System.out.println("waiting on " + registeredObject);
                    lock.wait();
                }

                System.out.println(name + " consumed: " + sharedQueue.take(registeredObject));

            } catch (InterruptedException ex) {
                logger.log(Level.SEVERE, name + "interrupted", ex);
                Thread.currentThread().interrupt();
            }
        }
    }
}



