package com.bounded.queue;

import com.bounded.queue.jobs.Consumer;
import com.bounded.queue.jobs.Producer;

public class ThreadSafeBoundedQueueTest {

    public static void main(String args[]) throws InterruptedException {

        BoundedQueue sharedQueue = new BoundedQueue(10);

        Object lockOne = new Object();
        Runnable producer1 = new Producer(sharedQueue, "producer1", lockOne);
        Runnable consumerOfOne1 = new Consumer(sharedQueue, "consumerOfOne1", lockOne, 1);
        Runnable consumerOfOne2 = new Consumer(sharedQueue, "consumerOfOne2", lockOne, 1);
        Runnable consumerOfOne3 = new Consumer(sharedQueue, "consumerOfOne3", lockOne, 1);

        Object lockTwo = new Object();
        Runnable producer2 = new Producer(sharedQueue, "producer2", lockTwo);
        Runnable consumerOfTwo = new Consumer(sharedQueue, "consumerOfTwo", lockTwo, 2);

        Thread t1 = new Thread(producer1);
        Thread t2 = new Thread(consumerOfOne1);
        Thread t3 = new Thread(consumerOfOne2);
        Thread t4 = new Thread(consumerOfOne3);
        Thread t5 = new Thread(producer2);
        Thread t6 = new Thread(consumerOfTwo);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}





