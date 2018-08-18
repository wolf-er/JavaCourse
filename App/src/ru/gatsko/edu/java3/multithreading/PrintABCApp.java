package ru.gatsko.edu.java3.multithreading;

public class PrintABCApp {
    public static void main(String[] args) {
        PrintLetters pabc = new PrintLetters();
        Thread t1 = new Thread(new ThreadLetters(pabc, "A", 0));
        Thread t2 = new Thread(new ThreadLetters(pabc, "B", 1));
        Thread t3 = new Thread(new ThreadLetters(pabc, "C", 2));
        t1.start();
        t2.start();
        t3.start();
    }
}

class ThreadLetters implements Runnable{
    PrintLetters pabc;
    String letter;
    int position;
    public ThreadLetters(PrintLetters pabc, String letter, int position) {
        this.pabc = pabc;
        this.letter = letter;
        this.position = position;
    }
    @Override
    public void run() {
        while (pabc.number < 15) pabc.printLetter(letter, position);
    }
}
class PrintLetters{
    public Integer number = 0;
    public synchronized void printLetter(String letter, Integer position){
        try {
            while (number % 3 != position) wait();
            if (number >= 15) return;
            System.out.print(letter);
            number++;
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}