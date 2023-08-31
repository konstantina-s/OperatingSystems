package ex4;

import java.util.HashSet;

public class Singleton extends Thread {

    private static volatile Singleton singleton;
    private static final Object mutex = new Object();

    private Singleton() {
    }

    public static Singleton getInstance() throws InterruptedException {
            Singleton instance = singleton;
            if (instance == null) {
                synchronized (mutex) {
                    instance = singleton;
                    if (instance == null) {
                        System.out.println("Singleton is instantiated");
                        singleton = instance = new Singleton();
                    }
                }
            }
            return instance;
        }

        @Override
        public void run () {
            try {
                getInstance();
                System.out.println("Thread tries to access singleton");
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }

        public static void main (String[]args) throws InterruptedException {

            HashSet<Thread> threads = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                threads.add(new Singleton());
            }

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join(2000);
            }
    }
}
