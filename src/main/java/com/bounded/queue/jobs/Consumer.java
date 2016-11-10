package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Callable<Integer> {

    private final BoundedQueue sharedQueue;
    private String name;

    @Override
    public Integer call() throws Exception {

        while(true){    //what is happening here?
            try {
                Integer element = (Integer) sharedQueue.take();

                if(element==null) {
                    int v = 0;
                }

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



