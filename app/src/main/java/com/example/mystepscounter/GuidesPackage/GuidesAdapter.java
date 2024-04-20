package com.example.mystepscounter.GuidesPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mystepscounter.R;

import java.util.ArrayList;

public class GuidesAdapter extends RecyclerView.Adapter<GuidesAdapter.MyViewHolder> {
    private final GuidesInterface guidesInterface;
    Context context;
    ArrayList<GuidesModel> guidesModels;
    public GuidesAdapter(Context context, ArrayList<GuidesModel> guidesModels,
                         GuidesInterface guidesInterface){
        this.context = context;
        this.guidesModels = guidesModels;
        this.guidesInterface = guidesInterface;
    }
    @NonNull
    @Override
    public GuidesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_exercise_group, parent, false);
        return new GuidesAdapter.MyViewHolder(view, guidesInterface);
    }
    @Override
    public void onBindViewHolder(@NonNull GuidesAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(guidesModels.get(position).getMuscle_group_exercise_name());
        holder.imageView.setImageResource(guidesModels.get(position).getImage_muscle_group());

    }
    @Override
    public int getItemCount() {
        return guidesModels.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView, GuidesInterface guidesInterface) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_muscle_group);
            textView = itemView.findViewById(R.id.textView_muscle_group);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(guidesInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            guidesInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
