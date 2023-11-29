package com.example.mystepscounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.LinearLayout;

import com.example.mystepscounter.FitNotesPackage.WorkoutAdapter;
import com.example.mystepscounter.FitNotesPackage.WorkoutInterface;
import com.example.mystepscounter.FitNotesPackage.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

public class FitNotes extends AppCompatActivity implements WorkoutInterface {
    RecyclerView recyclerView;
    Button btnAddWorkout;
    List<WorkoutItem> workoutList;
    WorkoutAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_fit_notes);

        recyclerView = findViewById(R.id.recycler_workout);
        btnAddWorkout = findViewById(R.id.btnAddWorkout);

        workoutList = new ArrayList<>();

        recyclerViewAdapter = new WorkoutAdapter(this, workoutList, this);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new FitNotes.SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
                // Process the workout name, for example, add it to your dataset
                if (!workoutName.isEmpty()) {
                    workoutList.add(new WorkoutItem(workoutName)); // Add the workout to your RecyclerView or data source
                    recyclerViewAdapter.notifyDataSetChanged(); // Notify adapter about the data change
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

                positiveButton.setTextColor(getResources().getColor(R.color.white)); // Change text color
                negativeButton.setTextColor(getResources().getColor(R.color.white)); // Change text color


            }
        });
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // Set width as needed
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Set height as needed
        dialog.getWindow().setAttributes(layoutParams);

        dialog.show();
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
            workoutList.remove(position);
            recyclerViewAdapter.notifyItemRemoved(position);

        }
    }
    @Override
    public void onWorkoutClick(int position) {
        Intent intent = new Intent(FitNotes.this, Workout.class);
        intent.putExtra("NAME", workoutList.get(position).getName());

        startActivity(intent);
    }
}