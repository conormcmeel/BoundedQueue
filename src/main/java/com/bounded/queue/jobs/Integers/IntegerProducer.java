package com.bounded.queue.jobs.Integers;

import com.bounded.queue.BoundedQueue;
import com.bounded.queue.jobs.Producer;

public class IntegerProducer extends Producer {

    private final BoundedQueue<Integer> sharedQueue;
    private final String name;
    private final int number_of_objects;

    public IntegerProducer(final BoundedQueue sharedQueue, final String name, final int numberOfObjects) {

        super(name);

        this.sharedQueue = sharedQueue;
        this.name = name;
        this.number_of_objects = numberOfObjects;
    }

    @Override
    public void run() {

        for(int i=1; i<=number_of_objects; i++) {

            try {

                sharedQueue.put(i);
                System.out.println(name + " produced: " + i);

            } catch (InterruptedException ex) {
                logException(ex);
            }
        }
    }
}
