package com.example.mystepscounter.FitNotesPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder> {
    List<WorkoutItem> workoutList;
    Context context;
    WorkoutInterface workoutInterface;

    public WorkoutAdapter(Context context, List<WorkoutItem> workoutList, WorkoutInterface workoutInterface) {
        this.context = context;
        this.workoutList = workoutList;
        this.workoutInterface = workoutInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_exercises, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutItem currentWorkout = workoutList.get(position);
        holder.textView_workout_name.setText(currentWorkout.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workoutInterface != null) {
                    workoutInterface.onWorkoutClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_workout_name;
        // You can include other TextViews for additional workout details

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_workout_name = itemView.findViewById(R.id.textView_exercise);
            // Initialize other TextViews if needed
        }
        // Add views for each item in the RecyclerView (e.g., TextViews)
    }
}
