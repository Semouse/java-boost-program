package com.example.concurrent.phaser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/**
 * There are five bus stops.
 * On the first four of them, passengers can stand and wait for the bus.
 * The bus leaves the station and stops at each stop for a while.
 * After the final stop, the bus goes to the station.
 * We need to pick up passengers and drop them off at the right stops.
 */
public class BusRoute {
    private static final Phaser PHASER = new Phaser(1);


    public static void main(String[] args) throws InterruptedException {

        List<Passenger> passengers = generatePassengers();

        for(int i = 0; i < 7; i++) {
            switch(i) {
                case 0:
                    System.out.println("Bus left bus station.");
                    PHASER.arrive();
                    break;
                case 6:
                    System.out.println("Bus arrived at bus station.");
                    PHASER.arriveAndDeregister();
                    break;
                default:
                    int currentBusStop = PHASER.getPhase();
                    System.out.println("Bus stop № " + currentBusStop);

                    for(Passenger p : passengers) {
                        if(p.departure == currentBusStop) {
                            PHASER.register();
                            p.start();
                        }
                    }
                    PHASER.arriveAndAwaitAdvance();
            }

            Thread.sleep(1000);
        }

    }

    private static List<Passenger> generatePassengers() {
        List<Passenger> passengers = new ArrayList<>();

        for(int i = 1; i < 5; i++) {
            if((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(i, i + 1));
            }

            if((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(i, 5));
            }
        }

        return passengers;
    }

    public static class Passenger extends Thread {
        private final int departure;
        private final int destination;

        public Passenger(int departure, int destination) {
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + " waits on bus stop № " + this.departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(this + " take a bus");

                while(PHASER.getPhase() < destination) {
                    PHASER.arriveAndAwaitAdvance();
                }

                Thread.sleep(2000);
                PHASER.arriveAndDeregister();
                System.out.println(this + " left the bus.");
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "Passenger {" + departure + " -> " + destination + '}';
        }
    }
}
