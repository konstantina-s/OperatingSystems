package ex5;

import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Vinegar {

    public static void main(String[] args) throws InterruptedException {
        HashSet<Thread> threads = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            threads.add(new C());
            threads.add(new H());
            threads.add(new H());
            threads.add(new O());
        }
        // run all threads in background
        for (Thread th: threads){
            th.start();
        }
        // after all of them are started, wait each of them to finish for maximum 2_000 ms
        for (Thread th: threads){
            th.join(2000);
        }
        for (Thread th:threads){
            if (th.isAlive()){
                // for each thread, terminate it if it is not finished
                System.out.println("Possible deadlock!");
            }
        }
        System.out.println("Process finished.");

    }
    static Semaphore c = new Semaphore(2);
    static Semaphore h = new Semaphore(4);
    static Semaphore o = new Semaphore(2);
    static Semaphore hHere = new Semaphore(0);
    static Semaphore oHere = new Semaphore(0);
    static Semaphore finished = new Semaphore(0);
    static Semaphore start = new Semaphore(0);
    static int count = 0;
    static Semaphore lock = new Semaphore(1);
    static class C extends Thread{

        public void execute() throws InterruptedException {
            c.acquire();
            System.out.println("C here.");
            lock.acquire();
            count++;
            if (count == 2) {
                hHere.acquire(4);
                oHere.acquire(2);
                start.release(8);
            }
            lock.release();
            start.acquire();
            System.out.println("Molecule bonding.");

            Thread.sleep(100);

            System.out.println("C done.");
            finished.release();

            lock.acquire();
            if (count == 2){
                count = 0;
                finished.acquire(8);
                System.out.println("Molecule created.");
                o.release(2);
                h.release(4);
                c.release(2);
            }
            lock.release();
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class H extends Thread{

        public void execute() throws InterruptedException {
            // at most 4 atoms should print this in parallel
            h.acquire();
            System.out.println("H here.");
            hHere.release();
            // after all atoms are present, they should start with the bonding process together
            start.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);// this represent the bonding process
            System.out.println("H done.");
            finished.release();
            // only one atom should print the next line, representing that the molecule is created
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class O extends Thread{

        public void execute() throws InterruptedException {
            // at most 2 atoms should print this in parallel
            o.acquire();
            System.out.println("O here.");
            // after all atoms are present, they should start with the bonding process together
            oHere.release();
            start.acquire();
            System.out.println("Molecule bonding.");
            Thread.sleep(100);// this represent the bonding process
            System.out.println("O done.");
            finished.release();
            // only one atom should print the next line, representing that the molecule is created
        }

        @Override
        public void run() {
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
