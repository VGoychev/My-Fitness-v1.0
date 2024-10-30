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
SharedPreferences sp;
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

    public static final String PREF_SELECTED_LANGUAGE = "selected_language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String selectedLanguage = sp.getString(PREF_SELECTED_LANGUAGE, "");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        if (!selectedLanguage.isEmpty()) {
            Locale newLocale = new Locale(selectedLanguage);
            Locale.setDefault(newLocale);
            Configuration configuration = getResources().getConfiguration();
            configuration.setLocale(newLocale);
            Context context = createConfigurationContext(configuration);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_menu);
        } else {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_menu);
        }
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

        String height = sp.getString("height" , "");
        String weight = sp.getString("weight" , "");
        String age = sp.getString("age" , "");
        String gender= sp.getString("gender", "");

        gender1.setText(getString(R.string.gender) + " " + gender);
        age1.setText(getString(R.string.age) + " " + age);
        height1.setText(getString(R.string.height) + " " + height);
        weight1.setText(getString(R.string.weight) + " " + weight);

        languageButton = findViewById(R.id.btn_language);
        languageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LanguageButton", "Language button clicked"); // Log message

                switchLanguage();
            }
        });
    }
    private void switchLanguage() {
        Locale newLocale = Locale.getDefault().getLanguage().equals("bg") ? Locale.ENGLISH : new Locale("bg");

        Locale.setDefault(newLocale);
        Configuration config = new Configuration();
        config.locale = newLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PREF_SELECTED_LANGUAGE, newLocale.getLanguage());
        String currentGender = sp.getString("gender", "");

        Log.d("SwitchLanguage", "Current gender value: " + currentGender);

        if (newLocale.getLanguage().equals("bg")) {
            if ("Male".equals(currentGender)) {
                currentGender = "Мъж";
            } else if ("Female".equals(currentGender)) {
                currentGender = "Жена";
            }
        } else { // English language
            if ("Мъж".equals(currentGender)) {
                currentGender = "Male";
            } else if ("Жена".equals(currentGender)) {
                currentGender = "Female";
            }
        }

        editor.putString("gender", currentGender);
        editor.apply();
        Log.d("SwitchLanguage", "Gender value updated to: " + sp.getString("gender", ""));
        Log.d("SwitchLanguage", "Switching language to " + newLocale.getLanguage());


        recreate();
    }
}