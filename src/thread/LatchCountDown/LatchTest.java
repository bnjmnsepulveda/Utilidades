/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread.LatchCountDown;

import java.util.Random;
import java.util.concurrent.*;

public class LatchTest {

    private static final int COUNT = 10;

    private static class Worker implements Runnable {

        CountDownLatch startLatch;
        CountDownLatch stopLatch;
        String name;

        Worker(CountDownLatch startLatch, CountDownLatch stopLatch, String name) {
            this.startLatch = startLatch;
            this.stopLatch = stopLatch;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                startLatch.await(); // wait until the latch has counted down to
                Random r = new Random();
                long s = r.nextInt(10)*1000;
                System.out.println("rand:" +s);
                Thread.sleep(s);
                // zero
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("Running: " + name);
            stopLatch.countDown();
        }
    }

    public static void main(String args[]) {
        // CountDownLatch(int count)
        // Constructs a CountDownLatch initialized with the given count.
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch stopSignal = new CountDownLatch(COUNT);
        for (int i = 0; i < COUNT; i++) {
            new Thread(new Worker(startSignal, stopSignal, Integer.toString(i)))
                    .start();
        }
        System.out.println("Go");
        startSignal.countDown();
        try {
            stopSignal.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Done");
    }
}
