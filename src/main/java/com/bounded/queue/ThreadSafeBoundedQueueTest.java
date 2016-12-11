package com.bounded.queue;

import com.bounded.queue.jobs.Consumer;
import com.bounded.queue.jobs.Producer;

public class ThreadSafeBoundedQueueTest {

    public static void main(String args[]) throws InterruptedException {

        BoundedQueue<Integer> sharedQueue = new BoundedQueue<>(5);

        Subject subject1 = new Subject("Subject1");
        Producer producer1 = new Producer(sharedQueue, "producer-1", subject1);
        Consumer consumer1 = new Consumer(sharedQueue, "consumer-1", subject1);
        subject1.addObserver(consumer1);

        Subject subject2 = new Subject("Subject2");
        Producer producer2 = new Producer(sharedQueue, "producer-2", subject2);
        Consumer consumer2 = new Consumer(sharedQueue, "consumer-2", subject2);
        subject2.addObserver(consumer2);

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

//        for(int i=0; i<1; i++) {
//
//            Subject subject = new Subject("Subject" + i+1);
//
//            Producer producer1 = new Producer(sharedQueue, "producer-1", subject);
//
//            Consumer consumer1 = new Consumer(sharedQueue, "consumer-1", subject);
//            Consumer consumer2 = new Consumer(sharedQueue, "consumer-2", subject);
//            Consumer consumer3 = new Consumer(sharedQueue, "consumer-3", subject);
//
//            subject.addObserver(consumer1);
//            subject.addObserver(consumer2);
//            subject.addObserver(consumer3);
//
//            Thread t1 = new Thread(producer1);
//            Thread t2 = new Thread(consumer1);
//            Thread t3 = new Thread(consumer2);
//            Thread t4 = new Thread(consumer3);
//
//            t1.start();
//            t2.start();
//            t3.start();
//            t4.start();
//        }
//    }
//}





