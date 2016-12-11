package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {

    private final BoundedQueue sharedQueue;
    private String name;

    @Override
    public void run() {

        while(true){    //what is happening here
            try {
                Integer element = (Integer) sharedQueue.take(1);

                System.out.println(name + " consumed: "+ element);
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Consumer(BoundedQueue sharedQueue, String name) {
        this.sharedQueue = sharedQueue;
        this.name = name;
    }
}



