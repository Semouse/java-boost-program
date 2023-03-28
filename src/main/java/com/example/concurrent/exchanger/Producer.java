package com.example.concurrent.exchanger;

import java.util.concurrent.Exchanger;

class Producer implements Runnable {
    private final Exchanger<DataBuffer> exchanger;
    private DataBuffer buffer;

    public Producer(Exchanger<DataBuffer> exchanger, DataBuffer buffer) {
        this.exchanger = exchanger;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i = 0; i < 3; i++) {
            buffer.addToBuffer("Added value: " + i);
            if(buffer.isFull()) {
                try {
                    buffer = exchanger.exchange(buffer);
                } catch(InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
