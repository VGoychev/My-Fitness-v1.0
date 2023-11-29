package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Workout extends AppCompatActivity {
    Button btnAddExercise;
    RecyclerView recyclerView;
    TextView textViewWorkoutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_workout);

        btnAddExercise = findViewById(R.id.btnAddExercise);
        recyclerView = findViewById(R.id.recycler_workoutExercise);
        textViewWorkoutName = findViewById(R.id.textView_workoutName);
        String name = getIntent().getStringExtra("NAME");

        textViewWorkoutName.setText(name);
    }
}