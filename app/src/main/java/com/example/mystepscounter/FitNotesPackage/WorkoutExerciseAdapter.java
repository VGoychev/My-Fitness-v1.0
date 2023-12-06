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

public class WorkoutExerciseAdapter extends RecyclerView.Adapter<WorkoutExerciseAdapter.ViewHolder> {
    List<WorkoutExerciseItem> exerciseList;
    Context context;
    public WorkoutExerciseAdapter(Context context, List<WorkoutExerciseItem> exerciseList){
        this.context = context;
        this.exerciseList = exerciseList;
    }
    @NonNull
    @Override
    public WorkoutExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_exercises, parent, false);
        return new WorkoutExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutExerciseAdapter.ViewHolder holder, int position) {
        WorkoutExerciseItem currentExercise = exerciseList.get(position);
        holder.textView_exercise_name.setText(currentExercise.getName());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_exercise_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_exercise_name = itemView.findViewById(R.id.textView_exercise);

        }
    }
}
