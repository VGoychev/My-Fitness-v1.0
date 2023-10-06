package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MacrosCalculator extends AppCompatActivity{
    private Spinner spinner;
    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_macros_calculator);
        TextView Food;
        Food = (TextView) findViewById(R.id.Food);
        spinner = (Spinner) findViewById(R.id.spinner);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String item = spinner.getSelectedItem().toString();
                Food.setText(item);
            }
        });
    }
}