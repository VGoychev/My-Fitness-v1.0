package com.example.mystepscounter.FitNotesPackage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorkoutItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "workout_name")
    public String workoutName;
    public String getWorkoutName() {
        return workoutName;
    }
    public int getId(){
        return id;
    }
}
