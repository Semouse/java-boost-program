package com.example.concurrent.exchanger;

import java.util.concurrent.Exchanger;

class Consumer implements Runnable {
    private final Exchanger<DataBuffer> exchanger;
    private DataBuffer buffer;

    public Consumer(Exchanger<DataBuffer> exchanger, DataBuffer buffer) {
        this.exchanger = exchanger;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i = 0; i < 3; i++) {
            System.out.println("waiting");
            try {
                buffer = exchanger.exchange(buffer);
                System.out.println("Received " + buffer.getFromBuffer());
            } catch(InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
