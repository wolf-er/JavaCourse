package ru.gatsko.edu.java2.collections;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {
    private HashMap<String, HashSet<String>> book = new HashMap<String, HashSet<String>>();
    public void add(String name, String phone){
        if (book.containsKey(name)){
            HashSet<String> phones = book.get(name);
            phones.add(phone);
            book.put(name, phones);
        } else {
            HashSet<String> phones = new HashSet<String>();
            phones.add(phone);
            book.put(name, phones);
        };
    }
    public HashSet<String> get(String name){
        if (book.containsKey(name)){
            return book.get(name);
        }
        return null;
    }
}
