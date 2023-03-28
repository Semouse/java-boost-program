package com.example.concurrent.deadlock;

import java.util.concurrent.locks.Lock;

public class RunnableA implements Runnable {

    private final Lock lockA;
    private final Lock lockB;

    public RunnableA(Lock lockA, Lock lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        System.out.println(threadName + " attempt to acquire lock A");
        lockA.lock();
        System.out.println(threadName + " acquire lock A");

        try {
            Thread.sleep(3000);
        } catch(InterruptedException exception) {
            exception.printStackTrace();
        }

        System.out.println(threadName + " attempt to acquire lock B");
        lockB.lock();
        System.out.println(threadName + " acquire lock B");

        System.out.println("release lock A");
        lockA.unlock();

        System.out.println("release lock B");
        lockB.unlock();
    }
}
