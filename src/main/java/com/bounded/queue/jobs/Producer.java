package com.bounded.queue.jobs;

import com.bounded.queue.BoundedQueue;
import com.bounded.queue.Subject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private final BoundedQueue sharedQueue;
    private String name;
    private Subject subject;

    @Override
    public void run() {

        try {

            sharedQueue.put(subject);
            System.out.println(name + " produced");

            System.out.println("Notifying consumer");
            subject.setProduced();

        } catch (InterruptedException ex) {
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Producer(BoundedQueue sharedQueue, String name, Subject subject) {
        this.sharedQueue = sharedQueue;
        this.name = name;
        this.subject = subject;
    }
}
