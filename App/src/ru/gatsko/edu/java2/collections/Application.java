package ru.gatsko.edu.java2.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Application {
    static void printUniqPlaces(ArrayList<String> places){
        HashSet<String> uniqPlaces = new HashSet<String>();
        for (String place: places){
            uniqPlaces.add(place);
        }
        System.out.println(uniqPlaces);
    }

    static void printCountedPlaces(ArrayList<String> places){
        HashMap<String, Integer> placesdict = new HashMap<String, Integer>();
        for (String place: places){
            if (placesdict.containsKey(place)){
                placesdict.put(place, placesdict.get(place) + 1);
            }
            else {
                placesdict.put(place, 1);
            }
        }
        System.out.println(placesdict);
    }

    public static void main(String[] args) {
        ArrayList<String> places = new ArrayList<String>(
                Arrays.asList("Moscow", "Kiev", "Minsk", "Tomsk", "Omsk", "Rostov", "Moscow", "Ufa", "Ufa", "Ufa", "Gomel", "Grodno", "Grodno", "Gomel", "Tomsk"));

        System.out.println(places);
        printUniqPlaces(places);
        printCountedPlaces(places);

        PhoneBook x = new PhoneBook();
        x.add("Alex","123");
        x.add("Gina","321");
        x.add("Alex","111");
        x.add("Alex","111");
        System.out.println(x.get("Alex"));
        System.out.println(x.get("Gina"));
        System.out.println(x.get("Serg"));
    }
}
