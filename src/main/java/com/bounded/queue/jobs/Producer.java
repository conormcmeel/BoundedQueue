package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Callable<Integer> {

    private final BoundedQueue sharedQueue;
    private String name;

    @Override
    public Integer call() throws Exception {

        for(int i=0; i<10; i++){
            try {
                sharedQueue.put(i);
                System.out.println(name + " produced: " + i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public Producer(BoundedQueue sharedQueue, String name) {
        this.sharedQueue = sharedQueue;
        this.name = name;
    }
}
