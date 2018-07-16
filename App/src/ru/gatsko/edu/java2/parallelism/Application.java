package ru.gatsko.edu.java2.parallelism;

public class Application {
    static final int size = 10000000;
    static final int h = size / 2;
    static final float[] a1 = new float[h];
    static final float[] a2 = new float[h];
    static final Application app = new Application();
    static final float[] arr = new float[size];

    private static void firstMethod(){
        for (int i = 0; i < size; i++) arr[i] = 1;
        final long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i /5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("First method: " + (System.currentTimeMillis() - a));
    }

    private static void secondMethod(){
        for (int i = 0; i < size; i++) arr[i] = 1;
        final long a = System.currentTimeMillis();
        System.arraycopy(arr,0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        final Thread thread1 = new Thread(() -> app.processValue(a1));
        final Thread thread2 = new Thread(() -> app.processValue(a2));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1,0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
        System.out.println("Second method: " + (System.currentTimeMillis() - a));
    }

    public void processValue(float[] array){
        for (int i = 0; i < h; i++){
            array[i] = (float)(array[i] * Math.sin(0.2f + i /5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    public static void main(String[] args) {
        secondMethod();
        secondMethod();
        firstMethod();
        firstMethod();
        secondMethod();
        secondMethod();
        firstMethod();
        secondMethod();

    }
}
