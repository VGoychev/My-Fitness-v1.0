package com.example.mystepscounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mystepscounter.FitNotesPackage.WorkoutAdapter;
import com.example.mystepscounter.FitNotesPackage.WorkoutExerciseItem;
import com.example.mystepscounter.FitNotesPackage.WorkoutInterface;
import com.example.mystepscounter.FitNotesPackage.WorkoutItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FitNotes extends AppCompatActivity implements WorkoutInterface {
    RecyclerView recyclerView;
    Button btnAddWorkout;
    TextView txtViewInstructions;
    List<WorkoutItem> workoutList;
    WorkoutAdapter recyclerViewAdapter;

    private static final String WORKOUT_LIST_KEY = "workoutList";

    private Map<String, List<WorkoutExerciseItem>> exercisesMap;

    private static final String EXERCISES_MAP_KEY = "EXERCISES_MAP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fit_notes);

        recyclerView = findViewById(R.id.recycler_workout);
        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        txtViewInstructions = findViewById(R.id.txtViewInstructions);

        workoutList = new ArrayList<>();

        recyclerViewAdapter = new WorkoutAdapter(this, workoutList, this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new FitNotes.SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        updateInstructionsVisibility();

        loadData();
        loadExercisesMap();
        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWorkoutDialog();
            }
        });
    }
    private void showAddWorkoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogBackground);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_workout, null);
        builder.setView(dialogView);

        final EditText workoutNameEditText = dialogView.findViewById(R.id.editText_workout_name);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String workoutName = workoutNameEditText.getText().toString().trim();

                if (!workoutName.isEmpty()) {
                    workoutList.add(new WorkoutItem(workoutName));
                    Log.d("WorkoutList", "Workout added: " + workoutName);
                    recyclerViewAdapter.notifyDataSetChanged();

                    exercisesMap.put(workoutName, new ArrayList<>());

                    saveData();
                    saveExercisesMap();

                    updateInstructionsVisibility();
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
            super(0, ItemTouchHelper.LEFT);// Set swipe directions here
        }
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Get the position of the swiped item
            int position = viewHolder.getAdapterPosition();

            // Remove the item from your list
            WorkoutItem deletedWorkout = workoutList.get(position);
            Log.d("SwipedWorkout", "Deleting workout: " + deletedWorkout.getName()); // Add this line

            workoutList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);

            deleteWorkout(deletedWorkout.getName());

            saveData();
            saveExercisesMap();

            recyclerViewAdapter.notifyItemRangeChanged(position, workoutList.size());

            updateInstructionsVisibility();
        }
    }
    public void deleteWorkout(String workoutName) {

        Iterator<WorkoutItem> iterator = workoutList.iterator();
        while (iterator.hasNext()) {
            WorkoutItem workoutItem = iterator.next();
            if (workoutItem.getName().equals(workoutName)) {
                iterator.remove();

                List<String> keysToRemove = new ArrayList<>();
                for (Map.Entry<String, List<WorkoutExerciseItem>> entry : exercisesMap.entrySet()) {
                    if (entry.getKey().equals(workoutName)) {
                        keysToRemove.add(entry.getKey());
                    }
                }
                for (String key : keysToRemove) {
                    exercisesMap.remove(key);
                    Log.d("deleteWorkout", "Exercises removed for workout: " + key);
                }
//                exercisesMap.remove(workoutName);

//                Iterator<Map.Entry<String, List<WorkoutExerciseItem>>> mapIterator = exercisesMap.entrySet().iterator();
//                while (mapIterator.hasNext()) {
//                    Map.Entry<String, List<WorkoutExerciseItem>> entry = mapIterator.next();
//                    if (entry.getKey().equals(workoutName)) {
//                        mapIterator.remove();
//                        Log.d("deleteWorkout", "Exercises removed for workout: " + workoutName);
//                        break; // Exit loop after removing exercises associated with the workout
//                    }
//                }
//                if (exercisesMap.containsKey(workoutName)) {
//                    exercisesMap.remove(workoutName); // Remove the entire workout entry from exercisesMap
//                    Log.d("deleteWorkout", "Exercises removed for workout: " + workoutName);
//                } else {
//                    Log.e("deleteWorkout", "No exercises found for workout: " + workoutName);
//                }

                saveData();
                saveExercisesMap();
                updateInstructionsVisibility();
                break;
            }
        }
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(workoutList);

        editor.putString(WORKOUT_LIST_KEY, json);
        editor.apply();
        Log.d("SaveData", "WorkoutList saved to SharedPreferences: " + json);

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(WORKOUT_LIST_KEY, "");

        Type type = new TypeToken<List<WorkoutItem>>(){}.getType();
        workoutList = gson.fromJson(json, type);

        if (workoutList == null) {
            workoutList = new ArrayList<>();
        }
        recyclerViewAdapter = new WorkoutAdapter(this, workoutList, this);

        recyclerView.setAdapter(recyclerViewAdapter);
        updateInstructionsVisibility();
        Log.d("LoadData", "WorkoutList loaded from SharedPreferences: " + json);

    }
    private void updateInstructionsVisibility() {
        if (workoutList.isEmpty()) {
            txtViewInstructions.setVisibility(View.VISIBLE); // Show textViewInstructions
        } else {
            txtViewInstructions.setVisibility(View.GONE); // Hide textViewInstructions
        }
    }

    private void loadExercisesMap() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(EXERCISES_MAP_KEY, "");

        Type type = new TypeToken<Map<String, List<WorkoutExerciseItem>>>(){}.getType();
        exercisesMap = gson.fromJson(json, type);

        if (exercisesMap == null) {
            exercisesMap = new HashMap<>();
        }
        Log.d("LoadExercisesMap", "ExercisesMap loaded from SharedPreferences: " + json);

    }

    private void saveExercisesMap() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(exercisesMap);

        editor.putString(EXERCISES_MAP_KEY, json);
        editor.apply();
    }

    @Override
    public void onWorkoutClick(int position) {
        if (position >= 0 && position < workoutList.size()) {
            String workoutName = workoutList.get(position).getName();

            // Handle the clicked workout
            if (exercisesMap.containsKey(workoutName)) {
                List<WorkoutExerciseItem> exercises = exercisesMap.get(workoutName);
                Log.d("WorkoutClick", "Exercises found for workout: " + workoutName + ", Exercise count: " + exercises.size());


                Intent intent = new Intent(FitNotes.this, Workout.class);
                intent.putExtra("WORKOUT_NAME", workoutName);
                startActivity(intent);
            } else {
                Log.e("FitNotes", "Workout not found in exercisesMap: " + workoutName);
            }
            } else {
            Log.e("FitNotes", "Invalid workout item clicked");
            }
    }
}