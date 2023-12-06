package com.example.mystepscounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mystepscounter.FitNotesPackage.WorkoutExerciseAdapter;
import com.example.mystepscounter.FitNotesPackage.WorkoutExerciseItem;
import com.example.mystepscounter.FitNotesPackage.WorkoutItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Workout extends AppCompatActivity {
    Button btnAddExercise;
    RecyclerView recyclerView;
    List<WorkoutExerciseItem> exerciseList;
    TextView textViewWorkoutName, textViewInstructions;
    WorkoutExerciseAdapter recyclerViewAdapter;
    Map<String, List<WorkoutExerciseItem>> exercisesMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_workout);

        btnAddExercise = findViewById(R.id.btnAddExercise);
        recyclerView = findViewById(R.id.recycler_workoutExercise);
        textViewWorkoutName = findViewById(R.id.textView_workoutName);
        textViewInstructions = findViewById(R.id.textViewInstructions);

        String selectedWorkoutName = getIntent().getStringExtra("WORKOUT_NAME");

        textViewWorkoutName.setText(selectedWorkoutName);

        exerciseList = new ArrayList<>();
        Log.d("WorkoutActivity", "Selected Workout Name: " + selectedWorkoutName);
        exercisesMap = loadExercisesMap();
//        exercisesMap = loadExercisesMap();

        if (exercisesMap == null) {
            exercisesMap = new HashMap<>();
        }


        exerciseList = exercisesMap.get(selectedWorkoutName);
        if (exerciseList == null) {
            exerciseList = new ArrayList<>();
            exercisesMap.put(selectedWorkoutName, exerciseList);
        }

        recyclerViewAdapter = new WorkoutExerciseAdapter(this, exerciseList);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new Workout.SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        updateInstructionsVisibility(exerciseList);

        loadExercisesMap();
        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddExerciseDialog();
            }
        });
    }
    private void showAddExerciseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogBackground);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_workout, null);
        builder.setView(dialogView);

        final EditText exerciseNameEditText = dialogView.findViewById(R.id.editText_workout_name);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String exerciseName = exerciseNameEditText.getText().toString().trim();
                if (!exerciseName.isEmpty()){

                    exerciseList.add(new WorkoutExerciseItem(exerciseName));
                    recyclerViewAdapter.notifyDataSetChanged();
                    Log.d("WorkoutActivity", "Added Exercise: " + exerciseName);
                    saveExercisesMap(exercisesMap);
                    updateInstructionsVisibility(exerciseList);

                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
           @Override
           public void onClick(DialogInterface dialog, int which){
               dialog.dismiss();
           }
        });

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);

                positiveButton.setTextColor(getResources().getColor(R.color.white));
                negativeButton.setTextColor(getResources().getColor(R.color.white));


            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
    }

    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.LEFT);
        }
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            Log.d("WorkoutActivity", "Deleting Exercise: " + exerciseList.get(position).getName());
            exerciseList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);

            saveExercisesMap(exercisesMap);
            updateInstructionsVisibility(exerciseList);
        }
    }

    private void saveExercisesMap(Map<String, List<WorkoutExerciseItem>> exercisesMap) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(exercisesMap);

        editor.putString("EXERCISES_MAP", json);
        editor.apply();

        Log.d("WorkoutActivity", "ExercisesMap saved to SharedPreferences: " + json);
    }

    private Map<String, List<WorkoutExerciseItem>> loadExercisesMap() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("EXERCISES_MAP", "");

        Type type = new TypeToken<Map<String, List<WorkoutExerciseItem>>>() {}.getType();

        Log.d("WorkoutActivity", "ExercisesMap loaded from SharedPreferences: " + json);

        return gson.fromJson(json, type);
    }
    private void updateInstructionsVisibility(List<WorkoutExerciseItem> exerciseList) {
        if (exerciseList.isEmpty()) {
            textViewInstructions.setVisibility(View.VISIBLE);
        } else {
            textViewInstructions.setVisibility(View.GONE);
        }
    }
}