package com.example.mystepscounter.FitNotesPackage;

import java.util.UUID;

public class WorkoutItem {
    String name;
    String id;
    public WorkoutItem(String name){
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }
    public String getName(){
        return name;
    }
}
