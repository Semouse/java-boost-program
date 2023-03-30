package com.example.concurrent.exchanger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Exchanger;


/**
 * There are two trucks: one goes from point A to point D, the other from point B to point C.
 * Roads AD and BC intersect at point E. From points A and B, you need to deliver packages to points C and D.
 * To do this, trucks in point E should meet and exchange appropriate parcels.
 */
public class Delivery {
    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        Truck scania = new Truck(1, "A", "D", Arrays.asList("package A->D", "package A->C"));
        Truck volvo = new Truck(2, "B", "C", Arrays.asList("package B->C", "package B->D"));

        new Thread(scania).start();
        Thread.sleep(100);
        new Thread(volvo).start();
    }

    public static class Truck implements Runnable {
        private final int number;
        private final String departure;
        private final String destination;
        private final List<String> cargo;

        public Truck(int number, String departure, String destination, List<String> cargo) {
            this.number = number;
            this.departure = departure;
            this.destination = destination;
            this.cargo = cargo;
        }

        @Override
        public void run() {
            try {
                System.out.println("Truck №" + number + " cargo: " + String.join(", ", cargo));
                System.out.println("Truck №" + number + " left from point " + departure + " to point " + destination);
                Thread.sleep(1000 + (long) (Math.random() * 5000));
                System.out.println("Truck №" + number + " arrived at point E");
                cargo.set(1, EXCHANGER.exchange(cargo.get(1)));
                System.out.println("Truck №" + number + " got cargo for point " + destination);
                Thread.sleep(1000 + (long) (Math.random() * 5000));
                System.out.println(
                    "Truck №" + number + " arrived at point " + destination + " and delivered: " + String.join(", ",
                        cargo));
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
