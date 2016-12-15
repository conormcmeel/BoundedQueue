package com.bounded.queue.jobs.Strings;

import com.bounded.queue.BoundedQueue;
import com.bounded.queue.jobs.Producer;

public class StringProducer extends Producer implements Runnable {

    private final BoundedQueue<String> sharedQueue;
    private final String name;
    private final int number_of_objects;

    public StringProducer(final BoundedQueue sharedQueue, final String name, final int numberOfObjects) {

        super(name);

        this.sharedQueue = sharedQueue;
        this.name = name;
        this.number_of_objects = numberOfObjects;
    }

    @Override
    public void run() {

        for(int i=1; i<=number_of_objects; i++){

            try {

                String value = "String" + i;
                sharedQueue.put(value);
                System.out.println(name + " produced: " + value);

            } catch (InterruptedException ex) {
                logException(ex);
            }
        }
    }
}
