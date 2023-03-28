package com.example.concurrent.phaser;

import java.util.concurrent.Phaser;

public class Main {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();

        new Thread(new CookingThread(phaser, "pot")).start();
        new Thread(new CookingThread(phaser, "knife")).start();
        new Thread(new CookingThread(phaser, "rope")).start();

        phaser.register();


        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + phaser.getPhase() + " is completed.");

        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + phaser.getPhase() + " is completed.");

        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + phaser.getPhase() + " is completed.");

        phaser.arriveAndDeregister();
    }
}
