package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mystepscounter.guides_package.adapters.ExerciseAdapter;
import com.example.mystepscounter.guides_package.interfaces.ExerciseInterface;
import com.example.mystepscounter.guides_package.models.ExerciseModel;

import java.util.ArrayList;

public class GuidesGroup extends AppCompatActivity implements ExerciseInterface {
    ArrayList<ExerciseModel> exerciseModels = new ArrayList<>();
    int [] muscle_group_chest_image = {R.drawable.bench_press, R.drawable.incline_bench_press, R.drawable.decline_bench_press,
            R.drawable.chest_flye, R.drawable.cable_crossover, R.drawable.dumbbell_bench_press, R.drawable.push_up, R.drawable.dip,
            R.drawable.machine_chest_press};
    int [] muscle_group_back_image = {R.drawable.deadlift, R.drawable.barbell_bent_over_row, R.drawable.upright_row,
            R.drawable.single_arm_bent_over_row, R.drawable.pull_up, R.drawable.chin_up, R.drawable.lat_pulldown_wide,
            R.drawable.lat_pulldown_close, R.drawable.seated_cable_row, R.drawable.shrugs};
    int [] muscle_group_abs_image = {R.drawable.plank, R.drawable.crunch, R.drawable.cable_crunch, R.drawable.leg_lift,
            R.drawable.bicycle_crunch, R.drawable.medicine_ball_slam, R.drawable.hanging_leg_raise};
    int [] muscle_group_arms_image = {R.drawable.barbell_biceps_curl, R.drawable.cable_press_down, R.drawable.dumbbell_hammer_curl,
            R.drawable.barbell_skull_crusher, R.drawable.preacher_curl, R.drawable.barbell_curls_with_reverse_grip,
            R.drawable.dumbbell_lateral_raise, R.drawable.overhead_press, R.drawable.facepull};
    int [] muscle_group_legs_image = {R.drawable.squat, R.drawable.romanian_deadlift, R.drawable.bulgarian_split_squad,
            R.drawable.leg_extension, R.drawable.standing_calf_raise, R.drawable.seated_calf_raise, R.drawable.hack_squat,
            R.drawable.lying_leg_curl};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_guides_group);
        String name = getIntent().getStringExtra("NAME");
        String description = getIntent().getStringExtra("DESCRIPTION");
        int image = getIntent().getIntExtra("IMAGE", 0);
        TextView groupNameTextView = findViewById(R.id.textView_muscle_group_bar);
        TextView descriptionTextView = findViewById(R.id.muscle_group_description);
        ImageView groupImageView = findViewById(R.id.imageView_muscle_group_activity);
        RecyclerView recyclerView = findViewById(R.id.recycler_exercises_list);
        groupNameTextView.setText(name);
        groupImageView.setImageResource(image);
        descriptionTextView.setText(description);
        String[] chestExercises;
        String[] armsExercises;
        String[] backExercises;
        String[] absExercises;
        String[] legsExercises;
        String[] chestExercisesDescription;
        String[] armsExercisesDescription;
        String[] backExercisesDescription;
        String[] absExercisesDescription;
        String[] legsExercisesDescription;
        String[] chestExercisesInstructions;
        String[] armsExercisesInstructions;
        String[] backExercisesInstructions;
        String[] absExercisesInstructions;
        String[] legsExercisesInstructions;
        switch (name) {
            case "Muscle Group Chest":
                chestExercises = getResources().getStringArray(R.array.muscle_group_chest_exercises);
                chestExercisesDescription = getResources().getStringArray(R.array.muscle_group_chest_exercises_description);
                chestExercisesInstructions = getResources().getStringArray(R.array.muscle_group_chest_exercises_instructions);
                for ( int i = 0; i < chestExercises.length; i++ ) {
                    exerciseModels.add(new ExerciseModel(chestExercises[i],muscle_group_chest_image[i],
                                            chestExercisesDescription[i],chestExercisesInstructions[i]));
            }
                break;
            case "Muscle Group Arms":
                armsExercises = getResources().getStringArray(R.array.muscle_group_arms_exercises);
                armsExercisesDescription = getResources().getStringArray(R.array.muscle_group_arms_exercises_description);
                armsExercisesInstructions = getResources().getStringArray(R.array.muscle_group_arms_exercises_instructions);
                for ( int i = 0; i < armsExercises.length; i++ ) {
                    exerciseModels.add(new ExerciseModel(armsExercises[i],muscle_group_arms_image[i],
                                            armsExercisesDescription[i],armsExercisesInstructions[i]));
                }
                break;
            case "Muscle Group Back":
                backExercises = getResources().getStringArray(R.array.muscle_group_back_exercises);
                backExercisesDescription = getResources().getStringArray(R.array.muscle_group_back_exercises_description);
                backExercisesInstructions = getResources().getStringArray(R.array.muscle_group_back_exercises_instructions);
                for ( int i = 0; i < backExercises.length; i++ ) {
                    exerciseModels.add(new ExerciseModel(backExercises[i],muscle_group_back_image[i],
                                            backExercisesDescription[i],backExercisesInstructions[i]));
                }
                break;
            case "Muscle Group Core":
                absExercises = getResources().getStringArray(R.array.muscle_group_abs_exercises);
                absExercisesDescription = getResources().getStringArray(R.array.muscle_group_abs_exercises_description);
                absExercisesInstructions = getResources().getStringArray(R.array.muscle_group_abs_exercises_instructions);
                for ( int i = 0; i < absExercises.length; i++ ) {
                    exerciseModels.add(new ExerciseModel(absExercises[i],muscle_group_abs_image[i],
                                            absExercisesDescription[i],absExercisesInstructions[i]));
                }
                break;
            case "Muscle Group Legs":
                legsExercises = getResources().getStringArray(R.array.muscle_group_legs_exercises);
                legsExercisesDescription = getResources().getStringArray(R.array.muscle_group_legs_exercises_description);
                legsExercisesInstructions = getResources().getStringArray(R.array.muscle_group_legs_exercises_instructions);
                for ( int i = 0; i < legsExercises.length; i++ ) {
                    exerciseModels.add(new ExerciseModel(legsExercises[i],muscle_group_legs_image[i],
                                            legsExercisesDescription[i],legsExercisesInstructions[i]));
                }
                break;
            default:
                Log.e("ExerciseModel", "Unexpected muscle group: " + name);
                break;

        }
        ExerciseAdapter adapter = new ExerciseAdapter(this, exerciseModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onExerciseClick(int position) {
        Intent intent = new Intent(GuidesGroup.this, MuscleGroupExercise.class);
            intent.putExtra("NAME", exerciseModels.get(position).getExerciseName());
            intent.putExtra("IMAGE_EXERCISE", exerciseModels.get(position).getMuscle_group_exercise_image());
            intent.putExtra("DESCRIPTION_EXERCISE",exerciseModels.get(position).getExerciseDescription());
            intent.putExtra("INSTRUCTION_EXERCISE",exerciseModels.get(position).getExerciseInstruction());
            startActivity(intent);
    }
}
