import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class lab1Exercise {
    //Exercise 3
    public static void main(String[] args) {
        //Creating BlockingQueue of size 10
        BlockingQueue<Message> queue = new BlockingQueue<>(10);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

class BlockingQueue<T> {

    List<T> contents;
    int capacity;

    public BlockingQueue(int capacity) {
        contents = new ArrayList<>();
        this.capacity = capacity;
    }

    public synchronized void enqueue(T item) throws InterruptedException{
        while(this.contents.size() == this.capacity)
            wait();

        if(this.contents.size() == 0)
            notifyAll();

        this.contents.add(item);
    }

    public synchronized T dequeue() throws InterruptedException{
        while(this.contents.size() == 0)
            wait();

        if(this.contents.size() == this.capacity)
            notifyAll();

        return this.contents.remove(0);
    }
}

class Producer implements Runnable {

    private BlockingQueue<Message> queue;

    public Producer(BlockingQueue<Message> q){
        this.queue = q;
    }
    @Override
    public void run() {

        for(int i=0; i<100; i++){
            Message msg = new Message("" + i);
            try {
                Thread.sleep(i);
                queue.enqueue(msg);
                System.out.println("Produce " + msg.getMsg());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Message msg = new Message("exit");
        try {
            queue.enqueue(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Consumer implements Runnable{

    private BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> q){
        this.queue = q;
    }

    @Override
    public void run() {
        try{
            Message msg;
            //consuming messages until exit message is received
            while(!(msg = (Message) queue.dequeue()).getMsg().equals("exit")){
                Thread.sleep(10);
                System.out.println("Consume " + msg.getMsg());
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Message {
    private String msg;

    public Message(String str){
        this.msg=str;
    }
    public String getMsg() {
        return msg;
    }
}






//Exercise 2
//        public static int NUM_RUNS = 100;
//        /**
//         * Promenlivata koja treba da go sodrzi brojot na pojavuvanja na elementot 3
//         */
//        int count = 0;
//        /**
//         * TODO: definirajte gi potrebnite elementi za sinhronizacija
//         */
//        Object obj=new Object();
//        public void init() {
//
//        }
//
//        class Counter extends Thread {
//
//            public void count(String data) throws InterruptedException {
//                // da se implementira
//                int pom=0;
//                for(int i=0; i<data.length(); i++)
//                {
//                    if(data.charAt(i)=='E')
//                    {
//                        pom++;
//                    }
//                }
//
//                synchronized (obj) {
//                    count+=pom;
//
//                }
//            }
//            private String data;
//
//            public Counter(String data) {
//                this.data = data;
//            }
//
//            @Override
//            public void run() {
//                try {
//                    count(data);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        public static void main(String[] args) {
//            try {
//                lab1Exercise environment = new lab1Exercise();
//                environment.start();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        public void start() throws Exception {
//            init();
//
//            HashSet<Thread> threads = new HashSet<Thread>();
//            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//
//            String pom = bf.readLine();
//            String [] data = pom.split("\n");
//
//            for(int i = 0; i< data.length; i++) {
//
//                Counter c = new Counter(data[i]);
//                threads.add(c);
//            }
//
//
//            for (Thread t : threads) {
//                t.start();
//            }
//
//            for (Thread t : threads) {
//                t.join();
//            }
//            System.out.println(count);
//        }
//    }



//        // Exercise 1
//        public static void main(String[] args) throws InterruptedException {
//
//            ThreadClassLettersNumbers letters = new ThreadClassLettersNumbers(1);
//            ThreadClassLettersNumbers numbers = new ThreadClassLettersNumbers('l');
//            Thread Letters = new Thread(letters);
//            Thread Numbers = new Thread(numbers);
//
//            Numbers.start();
//            Letters.start();
//
//        }
//    }
//    class ThreadClassLettersNumbers implements Runnable
//    {
////    List <Integer> num = new ArrayList<>();
////    List <Character> alph = new ArrayList<>() ;
//
//        Character ch;
//        Integer in;
//
//        public ThreadClassLettersNumbers(){};
//
//        public ThreadClassLettersNumbers(Character ch) {
//            this.ch = ch;
//            in = 0;
//        }
//
//        public ThreadClassLettersNumbers(Integer in) {
//            this.in = in;
//        }
//        @Override
//        public void run()
//        {
//            if(in==0)
//            {
//                for (int i = 0; i < 10; i++)
//                    System.out.println((char) (i + 65));
//            }else
//            {
//                for(int i = 0; i<10;i++)
//                    System.out.println(i);
//            }
//        }
//    }

        //Exercise 5
//    public static void main(String [] args) throws InterruptedException {
//
//        ThreadClass thread = new ThreadClass();
//        thread.start();
//        thread.join();
//        System.out.println(thread.getCount());
//    }
//}
//
//class ThreadClass extends Thread{
//    static int count = 0;
//    public void increment() {
//        count++;
//    }
//    public int getCount(){
//        return count;
//    }
//
//    @Override
//    public void run() {
//        for(int i = 0; i < 50; i++)
//            increment();
//    }