package com.bounded.queue.jobs.Integers;

import com.bounded.queue.BoundedQueue;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegerConsumer implements Runnable {

    private final BoundedQueue<Integer> sharedQueue;
    public final String name;       //make private
    private final Object lock = new Object();
    private final Integer registeredObject;
    private final Map<Object, Queue> register;
    private final static Logger logger = Logger.getLogger(IntegerConsumer.class.getName());

    public IntegerConsumer(final BoundedQueue sharedQueue, final String name, final Map<Object, Queue> register, Integer registeredObject) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.registeredObject = registeredObject;
        this.register = register;
    }

    @Override
    public void run() {

        synchronized (lock) {

            try {

                Queue locks = register.get(registeredObject);
                if(locks!=null) {
                    locks.add(lock);
                } else {
                    Queue consumers = new LinkedList<>();
                    consumers.add(lock);
                    register.put(registeredObject, consumers);
                }


                while (!sharedQueue.contains(registeredObject)) {
                    System.out.println(name + " waiting on " + registeredObject);
                    lock.wait();
                }

                System.out.println(name + " consumed: " + sharedQueue.take(registeredObject));

            } catch (InterruptedException ex) {
                logException(ex);
            }
        }
    }

    private void logException(InterruptedException ex) {
        logger.log(Level.SEVERE, name + "interrupted", ex);
        Thread.currentThread().interrupt();
    }
}



