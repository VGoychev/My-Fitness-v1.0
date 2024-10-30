package com.example.mystepscounter.macros_calculator_package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    List<FoodItem> foodItemList;
    Context context;
    public FoodAdapter(Context context, List<FoodItem> foodItemList) {
        this.context = context;
        this.foodItemList = foodItemList;
    }
    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_food_label, parent, false);
        return new FoodAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        FoodItem currentFood = foodItemList.get(position);
        holder.textView_food_name.setText(currentFood.getDisplayText());
        holder.textView_food_kcal.setText(String.valueOf(currentFood.getCalorie()));
        holder.textView_food_fat.setText(String.valueOf(currentFood.getFat()));
        holder.textView_food_carbs.setText(String.valueOf(currentFood.getCarbohydrate()));
        holder.textView_food_protein.setText(String.valueOf(currentFood.getProtein()));
    }
    @Override
    public int getItemCount() {
        return foodItemList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_food_name;
        TextView textView_food_kcal;
        TextView textView_food_fat;
        TextView textView_food_carbs;
        TextView textView_food_protein;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_food_name = itemView.findViewById(R.id.textView_food_name);
            textView_food_kcal = itemView.findViewById(R.id.textView_food_calories);
            textView_food_fat = itemView.findViewById(R.id.textView_food_fat);
            textView_food_carbs = itemView.findViewById(R.id.textView_food_carbs);
            textView_food_protein = itemView.findViewById(R.id.textView_food_protein);
        }
    }
}
