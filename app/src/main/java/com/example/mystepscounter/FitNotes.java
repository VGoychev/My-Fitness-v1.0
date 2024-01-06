package com.example.mystepscounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mystepscounter.FitNotesPackage.WorkoutAdapter;
import com.example.mystepscounter.FitNotesPackage.WorkoutInterface;
import com.example.mystepscounter.FitNotesPackage.WorkoutItem;
import java.util.List;


public class FitNotes extends AppCompatActivity implements WorkoutInterface{
    RecyclerView recyclerView;
    Button btnAddWorkout;
    TextView txtViewInstructions;
    private WorkoutAdapter recyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fit_notes);

        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        txtViewInstructions = findViewById(R.id.txtViewInstructions);
        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWorkoutDialog();
            }
        });
        initRecyclerView();

        loadWorkoutList();
        updateInstructionsVisibility();
        recyclerViewAdapter.setWorkoutInterface(this);
    }
    @Override
    public void onItemClick(WorkoutItem workoutItem) {
        Intent intent = new Intent(FitNotes.this, Workout.class);
        intent.putExtra("WORKOUT_NAME", workoutItem.getWorkoutName()); // Pass workout details
        intent.putExtra("WORKOUT_ID", workoutItem.getId());
        startActivity(intent);
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
                    saveNewWorkout(workoutName);
                    loadWorkoutList();
                    recyclerViewAdapter.notifyDataSetChanged();
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

    private void saveNewWorkout(String workoutName) {
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        WorkoutItem workoutItem = new WorkoutItem();
        workoutItem.workoutName = workoutName;
        database.workoutDao().insertWorkoutItem(workoutItem);
    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_workout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerViewAdapter = new WorkoutAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void loadWorkoutList() {
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        List<WorkoutItem> workoutList = database.workoutDao().getAllWorkoutItems();
        recyclerViewAdapter.setWorkoutList(workoutList);
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
            WorkoutItem deletedItem = recyclerViewAdapter.getWorkoutList().get(position);
            deleteItem(deletedItem);
            updateInstructionsVisibility();
        }
    }
    private void deleteItem(WorkoutItem workoutItem) {
        // Remove item from RecyclerView
        AppDatabase database = AppDatabase.getInstance(this.getApplicationContext());
        database.workoutDao().delete(workoutItem);
        recyclerViewAdapter.removeItem(workoutItem);

    }

    private void updateInstructionsVisibility() {
        if (recyclerViewAdapter.getItemCount() == 0) {
            txtViewInstructions.setVisibility(View.VISIBLE);
        } else {
            txtViewInstructions.setVisibility(View.GONE);
        }
    }

}