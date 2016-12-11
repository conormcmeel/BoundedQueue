package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable, Observer {

    private final BoundedQueue sharedQueue;
    private String name;
    private Observable observable;

    private void consume() {
        try {
            System.out.println(name + " consumed: "+ sharedQueue.take(1));
        } catch (InterruptedException ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update() {
       //this is called by the Subject to say its state has changed (do we need consumer to do this, or thr queue?
        consume();
        observable.removeObserver(this);
    }

    @Override
    public void run() {
//        while(true){
//            try {
//                System.out.println(name + " consumed: "+ sharedQueue.take(1));
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public Consumer(BoundedQueue sharedQueue, String name, Observable observable) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.observable = observable;
    }
}



