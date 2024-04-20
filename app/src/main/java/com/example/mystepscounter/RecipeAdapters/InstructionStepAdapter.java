package com.example.mystepscounter.RecipeAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;
import com.example.mystepscounter.RecipesModels.Step;

import java.util.List;

public class InstructionStepAdapter extends RecyclerView.Adapter<InstructionStepViewHolder>{
    Context context;
    List<Step> list;
    public InstructionStepAdapter(Context context, List<Step> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public InstructionStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionStepViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_steps, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull InstructionStepViewHolder holder, int position) {
        holder.textView_instruction_step_number.setText(String.valueOf(list.get(position).number));
        holder.textView_instruction_step_title.setText(list.get(position).step);
        holder.recycler_instruction_ingredients.setHasFixedSize(true);
        holder.recycler_instruction_ingredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionIngredientsAdapter instructionIngredientsAdapter = new InstructionIngredientsAdapter(context, list.get(position).ingredients);
        holder.recycler_instruction_ingredients.setAdapter(instructionIngredientsAdapter);
        holder.recycler_instruction_equipments.setHasFixedSize(true);
        holder.recycler_instruction_equipments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        InstructionEquipmentsAdapter instructionEquipmentsAdapter = new InstructionEquipmentsAdapter(context, list.get(position).equipment);
        holder.recycler_instruction_equipments.setAdapter(instructionEquipmentsAdapter);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionStepViewHolder extends RecyclerView.ViewHolder {
    TextView textView_instruction_step_number, textView_instruction_step_title;
    RecyclerView recycler_instruction_equipments, recycler_instruction_ingredients;
    public InstructionStepViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_instruction_step_number = itemView.findViewById(R.id.textView_instruction_step_number);
        textView_instruction_step_title = itemView.findViewById(R.id.textView_instruction_step_title);
        recycler_instruction_equipments = itemView.findViewById(R.id.recycler_instruction_equipments);
        recycler_instruction_ingredients = itemView.findViewById(R.id.recycler_instruction_ingredients);
    }
}
