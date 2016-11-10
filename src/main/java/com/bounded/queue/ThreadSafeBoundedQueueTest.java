package com.bounded.queue;

import com.bounded.queue.jobs.Consumer;
import com.bounded.queue.jobs.Producer;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSafeBoundedQueueTest {

    public static void main(String args[]) throws InterruptedException {

        BoundedQueue<Integer> sharedQueue = new BoundedQueue<>(10);

        Callable<Integer> producer1 = new Producer(sharedQueue, "producer-1");
        Callable<Integer> producer2 = new Producer(sharedQueue, "producer-2");
        Callable<Integer> consumer1 = new Consumer(sharedQueue, "consumer-1");
        Callable<Integer> consumer2 = new Consumer(sharedQueue, "consumer-2");

        Collection<Callable<Integer>> callables = new HashSet<>();
        callables.add(producer1);
        callables.add(producer2);
        callables.add(consumer1);
        callables.add(consumer2);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.invokeAll(callables);
    }
}





