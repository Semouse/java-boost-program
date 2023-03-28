package com.example.concurrent.counter;

public class Counter {
    private int count = 0;

    public int getCount() {
        return count ;
    }

    public synchronized void incrementCount() {
        ++count;
    }

}
