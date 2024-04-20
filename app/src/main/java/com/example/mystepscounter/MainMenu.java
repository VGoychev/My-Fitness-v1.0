package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainMenu extends AppCompatActivity {
Button btnMC , btnFN , btnRec , btnGuides, btnEdit, languageButton;
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

        languageButton = findViewById(R.id.btn_language);
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LanguageButton", "Language button clicked"); // Log message

                // Implement language switch logic here
                switchLanguage();
            }
        });
    }
    private void switchLanguage() {
        // Toggle between English and Bulgarian
        Locale newLocale = Locale.getDefault().getLanguage().equals("bg") ? Locale.ENGLISH : new Locale("bg");

        // Update app's locale
        Locale.setDefault(newLocale);
        Configuration config = new Configuration();
        config.locale = newLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        Log.d("SwitchLanguage", "Switching language to " + newLocale.getLanguage());

        // Restart MainActivity to apply changes (optional)
        recreate(); // Restart activity to apply language changes
    }
}