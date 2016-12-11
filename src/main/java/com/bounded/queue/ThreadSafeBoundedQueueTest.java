package com.bounded.queue;

import com.bounded.queue.jobs.Consumer;
import com.bounded.queue.jobs.Producer;

public class ThreadSafeBoundedQueueTest {

    public static void main(String args[]) throws InterruptedException {


        BoundedQueue<Integer> sharedQueue = new BoundedQueue<>(5);

        Runnable producer1 = new Producer(sharedQueue, "producer-1");
        Consumer consumer1 = new Consumer(sharedQueue, "consumer-1");

        Producer producer2 = new Producer(sharedQueue, "producer-2");
        Consumer consumer2 = new Consumer(sharedQueue, "consumer-2");

        Thread t1 = new Thread(producer1);
        Thread t2 = new Thread(consumer1);
        Thread t3 = new Thread(producer2);
        Thread t4 = new Thread(consumer2);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}





