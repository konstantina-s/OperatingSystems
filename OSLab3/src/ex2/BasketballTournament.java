package ex2;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class BasketballTournament {

    public static void main(String[] args) {
        HashSet<Player> threads = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Player bp = new Player();
            threads.add(bp);
        }

        for (Player bp : threads) {
            bp.start();
        }

        for (Player bp : threads) {
            try {
                bp.join(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Player bp : threads) {
            if (bp.isAlive()) {
                System.out.println("Possible deadlock!");
                bp.interrupt();
            }
        }

        System.out.println("Tournament finished.");
    }
}

class Player extends Thread {

    private static Semaphore arena = new Semaphore(20);
    private static Semaphore lockerRoom = new Semaphore(10);
    private static Semaphore gameWhistle = new Semaphore(1);

    private static Counter playersInLockerRoom = new Counter();
    private static Counter playersOnTheCourt = new Counter();

    public void execute() throws InterruptedException {
        arena.acquire();

        System.out.println("Player inside");

        lockerRoom.acquire();

        System.out.println("In dressing room…");

        playersInLockerRoom.increment();

        if (playersInLockerRoom.getCount() == 10) {
            playersInLockerRoom.setCount(0);
            lockerRoom.release(10);
        }

        Thread.sleep(10);


        playersOnTheCourt.increment();

        if (playersOnTheCourt.getCount() == 10) {
            gameWhistle.acquire();
        }

        System.out.println("Game started.");

        Thread.sleep(100);

        System.out.println("Player done.");

        playersOnTheCourt.decrement(); // players leaving the court...

        if (playersOnTheCourt.getCount() == 0) {
            System.out.println("Game finished.");
            gameWhistle.release();
            arena.release(10);
        }
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

class Counter {
    private int count;
    public Counter() {
        this.count = 0;
    }
    public synchronized void setCount(int c) {
        this.count = c;
    }
    public synchronized int getCount() {
        return this.count;
    }
    public synchronized void increment() {
        this.count++;
    }
    public synchronized void decrement() {
        this.count--;
    }
}