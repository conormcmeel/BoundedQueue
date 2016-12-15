package com.bounded.queue;

import com.bounded.queue.jobs.Consumer;
import com.bounded.queue.jobs.Integers.IntegerProducer;
import com.bounded.queue.jobs.Strings.StringProducer;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoundedQueueTest {

    private int number_of_objects = 5;

    @Test
    public void consumer1ShouldGetTheObject() throws InterruptedException {

        BoundedQueue<Integer> queue = new BoundedQueue(10);

        for(int i=1; i<=2; i++) {
            Consumer consumer = new Consumer(queue, "consumer" + i, 1);
            Thread t = new Thread(consumer);
            t.start();
        }

        Runnable producer = new IntegerProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        Thread.sleep(2000); //let threads finish before checking size

        assertEquals(4, queue.getSize());
        assertFalse(queue.contains(1));
    }

    @Test
    public void oneIntegerShouldRemainUnconsumed() throws InterruptedException {

        BoundedQueue<Integer> queue = new BoundedQueue(10);

        Runnable producer = new IntegerProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        for(int i=1; i<=4; i++) {
            Runnable consumer = new Consumer(queue, "consumer" + i, i);
            Thread t = new Thread(consumer);
            t.start();
        }

        Thread.sleep(2000); //let threads finish before checking size

        assertEquals(1, queue.getSize());
        assertTrue(queue.contains(5));
    }

    @Test
    public void queueShouldBeClearedOfAllIntegers() throws InterruptedException {

        BoundedQueue<Integer> queue = new BoundedQueue(10);

        Runnable producer = new IntegerProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        for(int i=1; i<=5; i++) {
            Runnable consumer = new Consumer(queue, "consumer" + i, i);
            Thread t = new Thread(consumer);
            t.start();
        }

        Thread.sleep(2000);

        assertEquals(0, queue.getSize());
    }

    @Test
    public void queueShouldBeClearedOfAllIntegersConsumersFirst() throws InterruptedException {

        BoundedQueue<Integer> queue = new BoundedQueue(10);

        for(int i=1; i<=5; i++) {
            Runnable consumer = new Consumer(queue, "consumer" + i, i);
            Thread t = new Thread(consumer);
            t.start();
        }

        Runnable producer = new IntegerProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        Thread.sleep(2000);

        assertEquals(0, queue.getSize());
    }

    @Ignore
    @Test
    public void stressTestOfIntegers() throws InterruptedException {

        BoundedQueue<Integer> queue = new BoundedQueue(10);
        Object lock = new Object();

        for(int x=0; x<10; x++) {
            Runnable producer = new IntegerProducer(queue, "producer", number_of_objects);
            Thread t1 = new Thread(producer);
            t1.start();

            for(int i=1; i<=5; i++) {
                Runnable consumer = new Consumer(queue, "consumer" + i, i);
                Thread t = new Thread(consumer);
                t.start();
            }
        }

        Thread.sleep(60000);

        assertEquals(0, queue.getSize());
    }

    @Test
    public void oneStringShouldRemainUnconsumed() throws InterruptedException {

        BoundedQueue<String> queue = new BoundedQueue(10);

        Runnable producer = new StringProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        for(int i=1; i<=4; i++) {
            Runnable consumer = new Consumer(queue, "consumer" + i, "String" + i);
            Thread t = new Thread(consumer);
            t.start();
        }

        Thread.sleep(2000);

        assertEquals(1, queue.getSize());
        assertTrue(queue.contains("String5"));
    }

    @Test
    public void queueShouldBeClearedOfAllStrings() throws InterruptedException {

        BoundedQueue<String> queue = new BoundedQueue(10);

        Runnable producer = new StringProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        for(int i=1; i<=5; i++) {
            Runnable consumer = new Consumer(queue, "consumer" + i, "String" + i);
            Thread t = new Thread(consumer);
            t.start();
        }

        Thread.sleep(2000);

        assertEquals(0, queue.getSize());
    }

    @Test
    public void queueShouldBeClearedOfAllStringsConsumersFirst() throws InterruptedException {

        BoundedQueue<String> queue = new BoundedQueue(10);

        for(int i=1; i<=5; i++) {
            Runnable consumer = new Consumer(queue, "consumer" + i, "String" + i);
            Thread t = new Thread(consumer);
            t.start();
        }

        Runnable producer = new StringProducer(queue, "producer", number_of_objects);
        Thread t1 = new Thread(producer);
        t1.start();

        Thread.sleep(2000);

        assertEquals(0, queue.getSize());
    }

    @Ignore
    @Test
    public void stressTestOfStrings() throws InterruptedException {

        BoundedQueue<String> queue = new BoundedQueue(10);

        for(int x=0; x<10; x++) {
            Runnable producer = new StringProducer(queue, "producer", number_of_objects);
            Thread t1 = new Thread(producer);
            t1.start();

            for(int i=1; i<=5; i++) {
                Runnable consumer = new Consumer(queue, "consumer" + i, "String" + i);
                Thread t = new Thread(consumer);
                t.start();
            }
        }

        Thread.sleep(60000);

        assertEquals(0, queue.getSize());
    }
}
