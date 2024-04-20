package com.example.mystepscounter.FitNotesPackage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = WorkoutItem.class,
        parentColumns = "id",
        childColumns = "workout_id",
        onDelete = ForeignKey.CASCADE))
public class ExerciseItem {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "exercise_name")
    public String exerciseName;
    @ColumnInfo(name = "workout_id")
    public int workoutId;
    public int getId() {
        return id;
    }
    public String getExerciseName() {
        return exerciseName;
    }
    public int getWorkoutId() {
        return workoutId;
    }
}
