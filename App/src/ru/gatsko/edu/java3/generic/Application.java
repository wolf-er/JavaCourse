package ru.gatsko.edu.java3.generic;

import java.util.ArrayList;

public class Application {
    static <T> void changePlaces(T[] massive, int indexFirst, int indexSecond){
        if (indexFirst >= massive.length || indexSecond >= massive.length) return;
        final T buffer = massive[indexFirst];
        massive[indexFirst] = massive[indexSecond];
        massive[indexSecond] =  buffer;
    }

    static <T> ArrayList<T> toArrayList(T[] baseMassive){
        ArrayList<T> newArray = new ArrayList<T>();
        for (T element: baseMassive) newArray.add(element);
        return newArray;
    }

    public static void main(String[] args) {
        Integer[] masInt = {10,20,30};
        String[] masStr = {"asd","fds","asd","reg"};
        changePlaces(masInt,0,2);
        for (Integer elem: masInt) System.out.println(elem);
        changePlaces(masStr,3,2);
        for (String elem: masStr) System.out.println(elem);

        Box<Apple> appleBox = new Box<>();
        Box<Apple> appleSecondBox = new Box<>();
        Box<Orange> orangeBox = new Box<>();

        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        appleBox.addFruit(new Apple());
        orangeBox.addFruit(new Orange());
        System.out.println(orangeBox.Compare(appleBox));
        orangeBox.addFruit(new Orange());
        System.out.println(orangeBox.Compare(appleBox));

        appleSecondBox.addFruit(new Apple());
        System.out.println("Веса коробок с яблоками " + appleSecondBox.getWeight() + " " + appleBox.getWeight());
        appleBox.putAllFruits(appleSecondBox);
        System.out.println("Веса коробок с яблоками " + appleSecondBox.getWeight() + " " + appleBox.getWeight());
        //appleBox.putAllFruits(orangeBox);

    }
}
