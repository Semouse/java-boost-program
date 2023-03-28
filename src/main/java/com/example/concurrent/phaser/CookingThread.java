package com.example.concurrent.phaser;

import java.util.concurrent.Phaser;

public class CookingThread implements Runnable {
    private final Phaser phaser;
    private final String name;

    public CookingThread(Phaser phaser, String name) {
        this.phaser = phaser;
        this.name = name;

        phaser.register();
    }

    @Override
    public void run() {
        System.out.println(name + " executes phase: " + phaser.getPhase());
        phaser.arriveAndAwaitAdvance();

        System.out.println(name + " executes phase: " + phaser.getPhase());
        phaser.arriveAndAwaitAdvance();

        System.out.println(name + " executes phase: " + phaser.getPhase());
        phaser.arriveAndDeregister();
    }
}
