package com.example.concurrent.exchanger;

import java.util.LinkedList;
import java.util.concurrent.Exchanger;

public class Main {

    public static void main(String[] args) {
        Exchanger<DataBuffer> exchanger = new Exchanger<>();

        new Thread(new Producer(exchanger, new DataBuffer(new LinkedList<>()))).start();
        new Thread(new Consumer(exchanger, new DataBuffer(new LinkedList<>()))).start();
    }

}

