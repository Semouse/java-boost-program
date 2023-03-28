package com.example.concurrent.counter;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class SynchronizedIncrement implements Runnable {
    private static AtomicInteger counter;
    private static final int limit = 100;
    private static final int threadPoolSize = 5;

    public static void main(String[] args) {
        counter = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        for(int i = 0; i < threadPoolSize; i++) {
            executorService.submit(new SynchronizedIncrement());
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        while(counter.get() < limit) {
            increaseCounter();
        }
    }

    private void increaseCounter() {
        System.out.println(Thread.currentThread().getName() + " : " + counter.getAndIncrement());
    }

}

