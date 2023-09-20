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

    //private static final String[] paths = {"Chicken with rice","Pork with potatoes", "Pizza"};
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
//        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MacrosCalculator.this,android.R.layout.simple_spinner_item,paths);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//    }
//    @Override
//    public void onItemSelected(AdapterView<?> paths, View v, int position, long id) {
//
//        switch (position) {
//            case 0:// Whatever you want to happen when the first item gets selected
//               System.out.println("a");
//               break;
//            case 1:
//                // Whatever you want to happen when the second item gets selected
//                break;
//            case 2:
//                // Whatever you want to happen when the thrid item gets selected
//                break;
//
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//        // TODO Auto-generated method stub
//    }
//}