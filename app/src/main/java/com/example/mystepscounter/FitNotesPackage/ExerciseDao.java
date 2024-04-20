package com.example.mystepscounter.FitNotesPackage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM exerciseitem WHERE workout_id = :workoutId") // Modify this query as per your database structure
    List<ExerciseItem> getExercisesForWorkout(int workoutId);
    @Query("SELECT * FROM exerciseitem WHERE id = :exerciseId")
    ExerciseItem getExerciseById(int exerciseId);
    @Insert
    void insertExerciseItem(ExerciseItem... exerciseItems);
    @Delete
    void delete(ExerciseItem exerciseItem);
}
