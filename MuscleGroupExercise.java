package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.widget.TextView;
import pl.droidsonroids.gif.GifImageView;

public class MuscleGroupExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_muscle_group_exercise);


        String exerciseName = getIntent().getStringExtra("NAME");
        int image_exercise = getIntent().getIntExtra("IMAGE_EXERCISE",0);
        String exerciseDescription = getIntent().getStringExtra("DESCRIPTION_EXERCISE");
        String exerciseInstruction = getIntent().getStringExtra("INSTRUCTION_EXERCISE");

        TextView exerciseTextView = findViewById(R.id.textView_muscle_group_exercise_bar);
        GifImageView exerciseImageView = findViewById(R.id.imageView_muscle_group_exercise_activity);
        TextView exerciseDescriptionTextView = findViewById(R.id.muscle_group_exercise_description);
        TextView exerciseInstructionTextView = findViewById(R.id.muscle_group_exercise_instruction);

        exerciseTextView.setText(exerciseName);
        exerciseImageView.setImageResource(image_exercise);
        exerciseDescriptionTextView.setText(exerciseDescription);
        exerciseInstructionTextView.setText(exerciseInstruction);

        exerciseInstructionTextView.setMovementMethod(new ScrollingMovementMethod());
    }
}