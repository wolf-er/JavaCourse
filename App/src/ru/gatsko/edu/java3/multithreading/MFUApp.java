package ru.gatsko.edu.java3.multithreading;

public class MFUApp {
    public static void main(String[] args) {
        MFU baseMFU = new MFU();
        Thread t1 = new Thread(new Jobs(baseMFU,"Первая дока", false));
        Thread t2 = new Thread(new Jobs(baseMFU,"Вторая дока", false));
        Thread t3 = new Thread(new Jobs(baseMFU,"Третья дока", true));
        Thread t4 = new Thread(new Jobs(baseMFU,"Четвертая дока", true));
        Thread t5 = new Thread(new Jobs(baseMFU,"Пятая дока", true));
        Thread t6 = new Thread(new Jobs(baseMFU,"Главная дока!", true));
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}

class Jobs implements Runnable{
    MFU mfu;
    String doc;
    Boolean isPrint;
    public Jobs(MFU mfu, String doc, Boolean isPrint){
        this.doc = doc;
        this.isPrint = isPrint;
        this.mfu = mfu;
    }
    @Override
    public void run() {
        if (isPrint) mfu.print.print(doc);
        else mfu.scan.scan(doc);
    }
}

class MFU{
    Scan scan = new Scan();
    Print print = new Print();
    class Scan{
        final Object lock = new Object();
        public void scan(String doc){
            synchronized (lock){
                System.out.println("Start scanning document " + doc);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Scanning document " + doc + " finished");
            }
        }
    }
    class Print{
        final Object lock = new Object();
        public void print(String doc){
            synchronized (lock){
                System.out.println("Start printing document " + doc);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Printing document " + doc + " finished");
            }
        }
    }



}