package ru.gatsko.edu.java3.multithreading;

import java.io.*;
import java.util.Random;

class FirstTh implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " started");
    }
}
class SecondTh extends Thread{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName() + " started");
    }

}

public class FileWriteApp{
    public static void main(String[] args) {
        Thread t1 = new Thread(new Filler("task2.txt"));
        Thread t2 = new Thread(new Filler("task2.txt"));
        Thread t3 = new Thread(new Filler("task2.txt"));
        t1.start();
        t2.start();
        t3.start();
    }
}
class Filler implements Runnable{
    BufferedWriter bw;
    Random r = new Random();
    String filename;
    Integer size = 10;
    public Filler(String filename){
        this.filename = filename;
    }
    public void writeTenSymbols(String filename, int size){
        //synchronized (filename) не обязательно, т.к. метод write записи в файл синхронизирован
        {
            try {
                bw = new BufferedWriter( new FileWriter(filename,true));
                bw.write(Thread.currentThread().getName() + ": ");
                for ( int i= 0 ; i < size ; i++) {
                    int rr = r.nextInt(90)+32;
                    bw.write((char) rr);
                }
                bw.write('\n');
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            writeTenSymbols(filename, size);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}