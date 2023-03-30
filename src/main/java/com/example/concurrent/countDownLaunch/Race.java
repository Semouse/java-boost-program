package com.example.concurrent.countDownLaunch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Five cars take part in the race. To start a race, the following conditions must be met:
 *     Each of the five cars pulled up to the starting line;
 *     The command "STEADY!" was given;
 *     The command "READY!" was given;
 *     The command "GO!" was given.
 *
 */
public class Race {
    private static final int TRACK_LENGTH = 500000;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startingCountdown = new CountDownLatch(8);

        for(int i = 1; i <= 5; i++) {
            new Thread(new Car(i, (int) (Math.random() * 100 + 50), startingCountdown)).start();
            Thread.sleep(1000);
        }

        Thread.sleep(1000);
        System.out.println("STEADY!");
        startingCountdown.countDown();

        Thread.sleep(1000);
        System.out.println("READY!");
        startingCountdown.countDown();

        Thread.sleep(1000);
        System.out.println("GO!");
        startingCountdown.countDown();
    }

    public static class Car implements Runnable {
        private static final AtomicInteger FINISHING_PLACE = new AtomicInteger(1);
        private final int carNumber;
        private final int carSpeed;
        private final CountDownLatch startingCountdown;

        public Car(int carNumber, int carSpeed, CountDownLatch startingCountdown) {
            this.carNumber = carNumber;
            this.carSpeed = carSpeed;
            this.startingCountdown = startingCountdown;
        }

        @Override
        public void run() {
            try {
                System.out.println("Car №" + carNumber + " took place on the starting grid");
                startingCountdown.countDown();
                startingCountdown.await();
                Thread.sleep(TRACK_LENGTH / carSpeed);
                System.out.println("Car №" + carNumber + " finished on position " + FINISHING_PLACE.getAndIncrement());
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
