package com.example.mystepscounter.fit_notes_package.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mystepscounter.fit_notes_package.models.SetItem;

import java.util.List;
@Dao
public interface SetDao {
    @Query("SELECT * FROM setitem WHERE exercise_id = :exerciseId")
    List<SetItem> getSetsForExercise(int exerciseId);
    @Query("SELECT * FROM SetItem ORDER BY id DESC LIMIT 1")
    SetItem getLastInsertedSetItem();
    @Insert
    void insertSetItem(SetItem... setItems);
    @Update
    void updateSetItem(SetItem setItem);
    @Delete
    void delete(SetItem setItem);
}
