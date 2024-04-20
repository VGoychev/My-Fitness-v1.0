package com.example.mystepscounter;

import static com.example.mystepscounter.MainMenu.PREF_SELECTED_LANGUAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.util.Locale;

public class Splash extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String selectedLanguage = sp.getString(PREF_SELECTED_LANGUAGE, "");
        if (!selectedLanguage.isEmpty()) {
            Locale newLocale = new Locale(selectedLanguage);
            Locale.setDefault(newLocale);
            Configuration configuration = new Configuration();
            configuration.setLocale(newLocale);
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(Splash.this,MainActivity.class));
            finish();
            }
        }, 3000);
    }
}