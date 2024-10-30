package com.example.mystepscounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.mystepscounter.fit_notes_package.models.ExerciseItem;
import com.example.mystepscounter.fit_notes_package.adapters.SetAdapter;
import com.example.mystepscounter.fit_notes_package.models.SetItem;
import com.example.mystepscounter.fit_notes_package.interfaces.SetItemChangeListener;

import java.util.List;

public class Exercise extends AppCompatActivity implements SetItemChangeListener {
    TextView setAdd, exerciseName, textViewInstructions;
    Button btnSetAdd;
    RecyclerView recyclerView;
    SetAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_exercise);

        setAdd = findViewById(R.id.textView_setAdd);
        exerciseName = findViewById(R.id.textView_exerciseName);
        btnSetAdd = findViewById(R.id.btnAddSet);
        textViewInstructions = findViewById(R.id.textView_setInstructions);
        String selectedExerciseName = getIntent().getStringExtra("EXERCISE_NAME");
        exerciseName.setText(selectedExerciseName);
        initRecyclerView();
        loadSetList();
        loadSavedValues();
        btnSetAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rep = 0;
                double weight = 0;
                int exerciseId = getIntent().getIntExtra("EXERCISE_ID", -1);

                addNewSet(rep, weight, exerciseId);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadSavedValues(); // Reload saved values every time the activity resumes
    }
    @Override
    public void onRepValueChanged(int position, int newValue) {
        List<SetItem> setList = recyclerViewAdapter.getSetList();
        if (setList != null && position >= 0 && position < setList.size()) {
            setList.get(position).setStoredRep(newValue);
            AppDatabase database = AppDatabase.getInstance(getApplicationContext());
            Log.d("SetItem", "New storedRep: " + setList.get(position).getStoredRep());
            database.setDao().updateSetItem(setList.get(position));
        }
    }
    @Override
    public void onWeightValueChanged(int position, double newValue) {
        List<SetItem> setList = recyclerViewAdapter.getSetList();
        if (setList != null && position >= 0 && position < setList.size()) {
            setList.get(position).setStoredWeight(newValue);
            AppDatabase database = AppDatabase.getInstance(getApplicationContext());
            database.setDao().updateSetItem(setList.get(position));
        }
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
            SetItem deletedItem = recyclerViewAdapter.getSetList().get(position);
            deleteItem(deletedItem);
        }
    }
    private void loadSavedValues() {
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        int exerciseId = getIntent().getIntExtra("EXERCISE_ID", -1);
        if (exerciseId != -1) {
            List<SetItem> setsForExercise = database.setDao().getSetsForExercise(exerciseId);
            setStoredValues(setsForExercise);
            recyclerViewAdapter.setSetList(setsForExercise);
            recyclerViewAdapter.notifyDataSetChanged();
        } else {
            Log.e("ExerciseActivity", "No exercise ID found in the intent");
        }
    }

    private void setStoredValues(List<SetItem> setsForExercise) {
        for (SetItem setItem : setsForExercise) {
            setItem.setStoredRep(setItem.getStoredRep());
            setItem.setStoredWeight(setItem.getStoredWeight());
        }
    }

    private void loadSetList() {
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        int exerciseId = getIntent().getIntExtra("EXERCISE_ID", -1);
        if (exerciseId != -1) {
            // Fetch exercises associated with the selected workout ID
            List<SetItem> setsForExercise = database.setDao().getSetsForExercise(exerciseId);
            recyclerViewAdapter.setSetList(setsForExercise);
            updateInstructionsVisibility();
        } else {
            Log.e("WorkoutActivity", "No workout ID found in the intent");
        }
    }
    private void addNewSet(int rep, double weight, int exercise_id) {
        int exerciseId = getIntent().getIntExtra("EXERCISE_ID", -1);
        if (exerciseId != -1) {
            AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
            ExerciseItem exerciseItem = database.exerciseDao().getExerciseById(exerciseId);
            if (exerciseItem != null) {
                SetItem setItem = new SetItem();
                setItem.setExercise_id(exercise_id);
                setItem.setRep(rep);
                setItem.setWeight(weight);

                database.setDao().insertSetItem(setItem);

                SetItem lastInsertedItem = database.setDao().getLastInsertedSetItem();
                if (lastInsertedItem != null) {
                    setItem.setId(lastInsertedItem.getId());

                    database.setDao().updateSetItem(setItem);
                    recyclerViewAdapter.addSet(setItem);

                    int position = recyclerViewAdapter.getSetList().indexOf(setItem);
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewAdapter.notifyItemChanged(position);
                    }
                    updateInstructionsVisibility();
                }
            }

        }
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_sets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerViewAdapter = new SetAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.setItemChangeListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new Exercise.SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void deleteItem(SetItem setItem) {
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        database.setDao().delete(setItem);
        recyclerViewAdapter.removeSetItem(setItem);
        updateInstructionsVisibility();
    }
    private void updateInstructionsVisibility() {
        if (recyclerViewAdapter.getItemCount() == 0) {
            textViewInstructions.setVisibility(View.VISIBLE);
        } else {
            textViewInstructions.setVisibility(View.GONE);
        }
    }
}
