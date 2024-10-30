package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.mystepscounter.guides_package.adapters.GuidesAdapter;
import com.example.mystepscounter.guides_package.interfaces.GuidesInterface;
import com.example.mystepscounter.guides_package.models.GuidesModel;

import java.util.ArrayList;

public class Guides extends AppCompatActivity implements GuidesInterface {
    ArrayList<GuidesModel> guidesModels = new ArrayList<>();
    int[] muscle_group_image = {R.drawable.chest, R.drawable.back, R.drawable.abs, R.drawable.biceps, R.drawable.leg};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_guides);
        RecyclerView recyclerView = findViewById(R.id.recycler_guides);
        setUpGuidesModels();
        GuidesAdapter guidesAdapter = new GuidesAdapter(this, guidesModels, this);
        recyclerView.setAdapter(guidesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setUpGuidesModels() {
        String[] muscle_group_exercise_name = getResources().getStringArray(R.array.muscle_group_exercise);
        String[] muscle_group_description = getResources().getStringArray(R.array.muscle_group_description);
        for (int i = 0; i < muscle_group_exercise_name.length; i++) {
            guidesModels.add(new GuidesModel(muscle_group_exercise_name[i],
                    muscle_group_image[i],
                    muscle_group_description[i]));
        }
    }
    @Override
    public void onItemClick(int position) {
            Intent intent = new Intent(Guides.this, GuidesGroup.class);
            intent.putExtra("NAME", guidesModels.get(position).getMuscle_group_exercise_name());
            intent.putExtra("DESCRIPTION", guidesModels.get(position).getMuscle_group_description());
            intent.putExtra("IMAGE", guidesModels.get(position).getImage_muscle_group());
            startActivity(intent);
    }
}