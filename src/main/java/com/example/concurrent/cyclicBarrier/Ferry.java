package com.example.concurrent.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * The ferry can transport three cars at the same time.
 * Send it when at least three cars gather at the crossing.
 */
public class Ferry {

    private static final CyclicBarrier BARRIER = new CyclicBarrier(3, new FerryBoat());

    public static void main(String[] args) throws InterruptedException {
        for(int i = 1; i <= 9; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }

    public static class FerryBoat implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
                System.out.println("Ferry transported cars!");
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Car implements Runnable {
        private final int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                System.out.println("The Car №" + carNumber + " arrived at the crossing.");
                BARRIER.await();
                System.out.println("The Car №" + carNumber + " left the ferry.");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
