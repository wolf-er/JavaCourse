package ru.gatsko.edu.java2.collections;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {
    private HashMap<String, HashSet<String>> book = new HashMap<String, HashSet<String>>();
    public void add(String name, String phone){
        HashSet<String> phones = new HashSet<String>();
        if (book.containsKey(name)){
            phones = book.get(name);
            phones.add(phone);
            book.put(name, phones);
        }
        phones.add(phone);
        book.put(name, phones);;
    }
    public HashSet<String> get(String name){
        if (book.containsKey(name)){
            return book.get(name);
        }
        return null;
    }
}
