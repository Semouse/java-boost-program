package com.example.concurrent.exchanger;

import java.util.Queue;

public class DataBuffer {
    private final Queue<String> data;

    public DataBuffer(Queue<String> data) {
        this.data = data;
    }

    public String getFromBuffer() {
        return data.poll();
    }

    public void addToBuffer(String value) {
        data.add(value);
    }

    public boolean isFull() {
        return data.size() == 1;
    }
}
