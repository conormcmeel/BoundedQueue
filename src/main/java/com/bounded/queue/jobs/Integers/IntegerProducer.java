package com.bounded.queue.jobs.Integers;

import com.bounded.queue.BoundedQueue;

import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegerProducer implements Runnable {

    private final BoundedQueue<Integer> sharedQueue;
    private final String name;
    private final Map<Object, Queue> register;
    private final static Logger logger = Logger.getLogger(IntegerProducer.class.getName());

    public IntegerProducer(final BoundedQueue sharedQueue, final String name, final Map<Object, Queue> register) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.register = register;
    }

    @Override
    public void run() {

        for(int i=1; i<=1; i++) {

            try {

                sharedQueue.put(i);
                System.out.println(name + " produced: " + i);

            } catch (InterruptedException ex) {
                logException(ex);
            } finally {

                Queue consumers = register.get(i);
                if(consumers!=null && consumers.size()>0) {
                    Object lock = consumers.poll();

                    synchronized (lock) {
                        lock.notify();
                    }
                }
            }
        }
    }

    private void logException(InterruptedException ex) {
        logger.log(Level.SEVERE, name + "interrupted", ex);
        Thread.currentThread().interrupt();
    }
}
