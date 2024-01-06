package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainMenu extends AppCompatActivity {
Button btnMC , btnFN , btnRec , btnGuides, btnEdit;

    public void btnFNClick(View view) {
        Intent intent = new Intent(this, FitNotes.class);
        startActivity(intent);
    }
    public void btnCalClick(View view) {
        Intent intent = new Intent(this, MacrosCalculator.class);
        startActivity(intent);
    }
    public void btnRecClick(View view){
        Intent intent = new Intent(this, Recipes.class);
        startActivity(intent);
    }
    public void btnGuidesClick(View view){
        Intent intent = new Intent(this, Guides.class);
        startActivity(intent);
    }
    public void btnEditClick(View view){
        SharedPreferences sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        sp.edit().remove("height").commit();
        sp.edit().remove("weight").commit();
        sp.edit().remove("age").commit();
        sp.edit().remove("gender").commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_menu);

        TextView age1 , height1 , weight1 , gender1;

        age1 = (TextView) findViewById(R.id.age1);
        height1 = (TextView) findViewById(R.id.height1);
        weight1 = (TextView) findViewById(R.id.weight1);
        gender1 = (TextView) findViewById(R.id.gender1);
        btnMC = (Button) findViewById(R.id.btnMC);
        btnFN = (Button) findViewById(R.id.btnFN);
        btnRec = (Button) findViewById(R.id.btnRec);
        btnGuides = (Button) findViewById(R.id.btnGuides);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String height = sp.getString("height" , "");
        String weight = sp.getString("weight" , "");
        String age = sp.getString("age" , "");
        String gender= sp.getString("gender", "");

        gender1.setText("Gender: " + gender);
        age1.setText("Age: " + age);
        height1.setText("Height(cm): " + height);
        weight1.setText("Weight(kg): " + weight);


    }
}