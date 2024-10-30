package com.example.mystepscounter.fit_notes_package.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mystepscounter.fit_notes_package.models.WorkoutItem;

import java.util.List;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workoutitem")
    List<WorkoutItem> getAllWorkoutItems();
    @Query("SELECT * FROM workoutitem WHERE id = :workoutId")
    WorkoutItem getWorkoutById(int workoutId);
    @Insert
    void insertWorkoutItem(WorkoutItem... workoutItems);
    @Delete
    void delete(WorkoutItem workoutItem);
}
