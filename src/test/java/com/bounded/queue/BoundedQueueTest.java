package com.bounded.queue;

import com.bounded.queue.jobs.Integers.IntegerConsumer;
import com.bounded.queue.jobs.Integers.IntegerProducer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

public class BoundedQueueTest {

    @Test
    public void oneIntegerShouldRemainUnconsumed() throws InterruptedException {


        Object lock = new Object();
        BoundedQueue<Integer> queue = new BoundedQueue(10);
        Map<Object, Queue> register = new HashMap<>();

        for(int i=1; i<=2; i++) {
            IntegerConsumer consumer = new IntegerConsumer(queue, "consumer" + i, register, 1);
            Thread t = new Thread(consumer);
            t.start();
        }

        Runnable producer = new IntegerProducer(queue, "producer", register);
        Thread t1 = new Thread(producer);
        t1.start();

        Thread.sleep(2000); //let threads finish before checking size

        assertEquals(0, queue.getSize());
      //  assertTrue(queue.contains(5));
    }

//    @Test
//    public void oneIntegerShouldRemainUnconsumed() throws InterruptedException {
//
//        BoundedQueue<Integer> queue = new BoundedQueue(10);
//        Object lock = new Object();
//        IntegerConsumer[] consumers = new IntegerConsumer[4];
//
//        for(int i=1; i<=4; i++) {
//            IntegerConsumer consumer = new IntegerConsumer(queue, "consumer" + i, lock, i);
//            consumers[i-1] = consumer;
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Runnable producer = new IntegerProducer(queue, "producer", null, consumers);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        Thread.sleep(2000); //let threads finish before checking size
//
//        assertEquals(1, queue.getSize());
//        assertTrue(queue.contains(5));
//    }


//    @Test
//    public void oneIntegerShouldRemainUnconsumed() throws InterruptedException {
//
//        BoundedQueue<Integer> queue = new BoundedQueue(10);
//        Object lockOne = new Object();
//
//        Runnable producer = new IntegerProducer(queue, "producer", lockOne);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        for(int i=1; i<=4; i++) {
//            Runnable consumer = new IntegerConsumer(queue, "consumer" + i, lockOne, i);
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Thread.sleep(2000); //let threads finish before checking size
//
//        assertEquals(1, queue.getSize());
//        assertTrue(queue.contains(5));
//    }
//
//    @Test
//    public void queueShouldBeClearedOfAllIntegers() throws InterruptedException {
//
//        BoundedQueue<Integer> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        Runnable producer = new IntegerProducer(queue, "producer", lock);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        for(int i=1; i<=5; i++) {
//            Runnable consumer = new IntegerConsumer(queue, "consumer" + i, lock, i);
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Thread.sleep(2000);
//
//        assertEquals(0, queue.getSize());
//    }
//
//    @Test
//    public void queueShouldBeClearedOfAllIntegersConsumersFirst() throws InterruptedException {
//
//        BoundedQueue<Integer> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        for(int i=1; i<=5; i++) {
//            Runnable consumer = new IntegerConsumer(queue, "consumer" + i, lock, i);
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Runnable producer = new IntegerProducer(queue, "producer", lock);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        Thread.sleep(2000);
//
//        assertEquals(0, queue.getSize());
//    }
//
//    @Ignore
//    @Test
//    public void stressTestOfIntegers() throws InterruptedException {
//
//        BoundedQueue<Integer> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        for(int x=0; x<10; x++) {
//            Runnable producer = new IntegerProducer(queue, "producer", lock);
//            Thread t1 = new Thread(producer);
//            t1.start();
//
//            for(int i=1; i<=5; i++) {
//                Runnable consumer = new IntegerConsumer(queue, "consumer" + i, lock, i);
//                Thread t = new Thread(consumer);
//                t.start();
//            }
//        }
//
//        Thread.sleep(60000);
//
//        assertEquals(0, queue.getSize());
//    }
//
//    @Test
//    public void oneStringShouldRemainUnconsumed() throws InterruptedException {
//
//        BoundedQueue<String> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        Runnable producer = new StringProducer(queue, "producer", lock);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        for(int i=1; i<=4; i++) {
//            Runnable consumer = new StringConsumer(queue, "consumer" + i, lock, "String" + i);
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Thread.sleep(2000);
//
//        assertEquals(1, queue.getSize());
//        assertTrue(queue.contains("String5"));
//    }
//
//    @Test
//    public void queueShouldBeClearedOfAllStrings() throws InterruptedException {
//
//        BoundedQueue<String> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        Runnable producer = new StringProducer(queue, "producer", lock);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        for(int i=1; i<=5; i++) {
//            Runnable consumer = new StringConsumer(queue, "consumer" + i, lock, "String" + i);
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Thread.sleep(2000);
//
//        assertEquals(0, queue.getSize());
//    }
//
//    @Test
//    public void queueShouldBeClearedOfAllStringsConsumersFirst() throws InterruptedException {
//
//        BoundedQueue<String> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        for(int i=1; i<=5; i++) {
//            Runnable consumer = new StringConsumer(queue, "consumer" + i, lock, "String" + i);
//            Thread t = new Thread(consumer);
//            t.start();
//        }
//
//        Runnable producer = new StringProducer(queue, "producer", lock);
//        Thread t1 = new Thread(producer);
//        t1.start();
//
//        Thread.sleep(2000);
//
//        assertEquals(0, queue.getSize());
//    }
//
//    @Ignore
//    @Test
//    public void stressTestOfStrings() throws InterruptedException {
//
//        BoundedQueue<String> queue = new BoundedQueue(10);
//        Object lock = new Object();
//
//        for(int x=0; x<10; x++) {
//            Runnable producer = new StringProducer(queue, "producer", lock);
//            Thread t1 = new Thread(producer);
//            t1.start();
//
//            for(int i=1; i<=5; i++) {
//                Runnable consumer = new StringConsumer(queue, "consumer" + i, lock, "String" + i);
//                Thread t = new Thread(consumer);
//                t.start();
//            }
//        }
//
//        Thread.sleep(60000);
//
//        assertEquals(0, queue.getSize());
//    }
}
