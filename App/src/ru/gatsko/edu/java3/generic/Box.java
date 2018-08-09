package ru.gatsko.edu.java3.generic;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private ArrayList<T> fruits;

    public Box(){
        fruits = new ArrayList<T>();
    }
    public void addFruit(T fruit){
        fruits.add(fruit);
    }
    public T getFruit(){
        return fruits.remove(0);
    }
    public float getWeight(){
        float result = 0.0f;
        for (T fruit: fruits) {result += fruit.getWeight();}
        return result;
    }
    public boolean Compare(Box<?> box) {
        return this.getWeight() == box.getWeight();
    }
    public void putAllFruits(Box<T> newBox) {
        while (!fruits.isEmpty())
            newBox.addFruit(fruits.remove(0));
    }

}
