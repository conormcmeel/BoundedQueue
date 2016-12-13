package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final BoundedQueue sharedQueue;
    private final String name;
    private final Object lock;
    private final Integer registeredObject;

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
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                Thread.currentThread().interrupt();
            } finally {
                lock.notifyAll();
            }
        }
    }

    public Consumer(final BoundedQueue sharedQueue, final String name, final Object lock, final Integer registeredObject) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.lock = lock;
        this.registeredObject = registeredObject;
    }
}



