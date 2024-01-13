package com.example.mystepscounter.FitNotesPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;

import java.util.Collections;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    private List<ExerciseItem> exerciseList;
    private Context context;
    private ExerciseInterface exerciseInterface;
    public void moveExerciseItem(int fromPosition, int toPosition) {
        Collections.swap(exerciseList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
    public void setExerciseInterface(ExerciseInterface exerciseInterface) {
        this.exerciseInterface = exerciseInterface;
    }
    public void setExerciseList(List<ExerciseItem> exerciseList){
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }
    public List<ExerciseItem> getExerciseList() {
        return exerciseList;
    }
    public void removeExerciseItem(ExerciseItem exerciseItem) {
        int position = exerciseList.indexOf(exerciseItem);
        if (position != -1) {
            exerciseList.remove(position);
            notifyItemRemoved(position);
        }
    }
    public ExerciseAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_exercises, parent, false);
        return new ExerciseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.ViewHolder holder, int position) {
        holder.textView_exercise_name.setText(this.exerciseList.get(position).exerciseName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exerciseInterface != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        exerciseInterface.onItemClick(exerciseList.get(position));
                    }
                }
            }
        });
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
