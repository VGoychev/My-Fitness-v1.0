package com.example.mystepscounter.GuidesPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {
    private final ExerciseInterface exerciseInterface;
    Context context;
    ArrayList<ExerciseModel> exerciseModels;
    public ExerciseAdapter(Context context, ArrayList<ExerciseModel> exerciseModels, ExerciseInterface exerciseInterface){
        this.context = context;
        this.exerciseModels = exerciseModels;
        this.exerciseInterface = exerciseInterface;

    }

    @NonNull
    @Override
    public ExerciseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_exercises, parent, false);
        return new ExerciseAdapter.MyViewHolder(view, exerciseInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.MyViewHolder holder, int position) {
        holder.textView_exercise.setText(exerciseModels.get(position).getExerciseName());
    }

    @Override
    public int getItemCount() {
        return exerciseModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView_exercise;
        public MyViewHolder(@NonNull View itemView, ExerciseInterface exerciseInterface) {
            super(itemView);
            textView_exercise = itemView.findViewById(R.id.textView_exercise);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (exerciseInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            exerciseInterface.onExerciseClick(pos);
                        }
                    }
                }
            });
        }
    }
}
