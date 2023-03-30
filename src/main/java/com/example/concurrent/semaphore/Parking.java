package com.example.concurrent.semaphore;


import java.util.concurrent.Semaphore;

/**
 * There is a car park that can accommodate a maximum of 5 cars at a time.
 * If the parking lot is full, then a newly arrived car must wait until at least one lot is free.
 * After that, it can park.
 */
public class Parking {
    private static final int NUMBER_OF_PARKING_PLACES = 5;
    private static final int CARS_TO_PARK = 7;

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(NUMBER_OF_PARKING_PLACES, true);

        for(int i = 1; i <= CARS_TO_PARK; i++) {
            new Thread(new Event(i, semaphore)).start();
            Thread.sleep(300);
        }
    }

    public static class Event implements Runnable {
        private final int carNumber;
        private final Semaphore semaphore;

        public Event(int carNumber, Semaphore semaphore) {
            this.carNumber = carNumber;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            System.out.println("Car №" + carNumber + " arrived.");
            try {
                semaphore.acquire();
                System.out.println("Car №" + carNumber + " is parked.");
                Thread.sleep(3000);
                System.out.println("Car №" + carNumber + " left the parking lot.");
                semaphore.release();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
