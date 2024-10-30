package com.example.mystepscounter.recipe_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;
import com.example.mystepscounter.recipes_models.Equipment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstructionEquipmentsAdapter extends RecyclerView.Adapter<InstructionEquipmentsViewHolder>{
    Context context;
    List<Equipment> list;
    public InstructionEquipmentsAdapter(Context context, List<Equipment> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public  InstructionEquipmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InstructionEquipmentsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_instructions_step_items, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull InstructionEquipmentsViewHolder holder, int position) {
        holder.textView_instructions_step_item.setText(list.get(position).name);
        holder.textView_instructions_step_item.setSelected(true);
        String imageUrl = list.get(position).image;
        Picasso.get().load(imageUrl).into(holder.imageView_instructions_step_items);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
class InstructionEquipmentsViewHolder  extends RecyclerView.ViewHolder {
    ImageView imageView_instructions_step_items;
    TextView textView_instructions_step_item;
    public InstructionEquipmentsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView_instructions_step_items = itemView.findViewById(R.id.imageView_instructions_step_items);
        textView_instructions_step_item = itemView.findViewById(R.id.textView_instructions_step_item);
    }
}