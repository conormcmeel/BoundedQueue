package com.bounded.queue;

import com.bounded.queue.jobs.Consumer;
import com.bounded.queue.jobs.Producer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThreadSafeBoundedQueueTest {

    private BoundedQueue queue;

    @Before
    public void setUp() {
        queue = new BoundedQueue(10);
    }

    @Test
    public void fiveConsumersConsumeFiveObjects() throws InterruptedException {

        Object lockOne = new Object();
        Runnable producer1 = new Producer(queue, "producer1", lockOne);
        Runnable consumerOfOne1 = new Consumer(queue, "consumerOfOne1", lockOne, 1);
        Runnable consumerOfOne2 = new Consumer(queue, "consumerOfOne2", lockOne, 2);
        Runnable consumerOfOne3 = new Consumer(queue, "consumerOfOne3", lockOne, 3);
        Runnable consumerOfOne4 = new Consumer(queue, "consumerOfOne4", lockOne, 4);
        Runnable consumerOfOne5 = new Consumer(queue, "consumerOfOne5", lockOne, 5);


        Thread t1 = new Thread(producer1);
        Thread t2 = new Thread(consumerOfOne1);
        Thread t3 = new Thread(consumerOfOne2);
        Thread t4 = new Thread(consumerOfOne3);
        Thread t5 = new Thread(consumerOfOne4);
        Thread t6 = new Thread(consumerOfOne5);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();

        Thread.sleep(5000);

        assertEquals(0, queue.getSize());
    }

    @Test
    public void fourConsumersOneObjectRemainsInQueue() throws InterruptedException {

        Object lockOne = new Object();
        Runnable producer1 = new Producer(queue, "producer1", lockOne);
        Runnable consumerOfOne1 = new Consumer(queue, "consumerOfOne1", lockOne, 1);
        Runnable consumerOfOne2 = new Consumer(queue, "consumerOfOne2", lockOne, 2);
        Runnable consumerOfOne3 = new Consumer(queue, "consumerOfOne3", lockOne, 3);
        Runnable consumerOfOne4 = new Consumer(queue, "consumerOfOne4", lockOne, 4);


        Thread t1 = new Thread(producer1);
        Thread t2 = new Thread(consumerOfOne1);
        Thread t3 = new Thread(consumerOfOne2);
        Thread t4 = new Thread(consumerOfOne3);
        Thread t5 = new Thread(consumerOfOne4);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        Thread.sleep(5000);

        assertEquals(1, queue.getSize());
    }
}
