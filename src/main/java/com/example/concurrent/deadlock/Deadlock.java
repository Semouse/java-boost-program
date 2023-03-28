package com.example.concurrent.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

    public static void main(String[] args) {
        Lock lockA = new ReentrantLock();
        Lock lockB = new ReentrantLock();

        Thread threadA = new Thread(new RunnableA(lockA, lockB));
        Thread threadB = new Thread(new RunnableB(lockA, lockB));

        threadA.start();
        threadB.start();
    }
}
