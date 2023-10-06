package com.example.mystepscounter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText enterHeight, enterWeight, enterAge;

    private double height;
    private double weight;
    private double age;
    String selectedRadiobut;
    Button button;
    RadioButton radioMale, radioFemale;
    RadioGroup radioGroup;
    SharedPreferences sp;

    private boolean heightIsValid() {

        if (enterHeight.getText().toString().length() == 0) {
            enterHeight.setError("Cant be empty");
            return false;
        }

        double height = Double.valueOf(enterHeight.getText().toString());

        if (height <= 0) {

            enterHeight.setError("Cant be zero");
            return false;
        }
        if (height <= 40 || height > 250) {

            enterHeight.setError("Cant be less than 40 cm or more than 250 cm");
            return false;
        }
        return true;
    }

    private boolean weightIsValid() {
        if (enterWeight.getText().toString().length() == 0) {
            enterWeight.setError("Cant be empty");
            return false;
        }

        double weight = Double.valueOf(enterWeight.getText().toString());

        if (weight <= 0) {

            enterWeight.setError("Cant be zero");
            return false;
        }
        if (weight > 600) {

            enterWeight.setError("Cant be more than 600 kg");
            return false;
        }
        return true;
    }

    private boolean ageIsValid() {
        if (enterAge.getText().toString().length() == 0) {
            enterAge.setError("Cant be empty");
            return false;
        }

        double age = Double.valueOf(enterAge.getText().toString());

        if (age <= 0) {

            enterAge.setError("Cant be zero");
            return false;
        }
        if (age > 116) {

            enterAge.setError("Cant be older than 116 years");
            return false;
        }
        return true;
    }

    private boolean genderIsSelected() {

        if (radioGroup.getCheckedRadioButtonId() == -1) { //checking if the user has selected a option using id
            Toast toast = Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        if (radioMale.isChecked()) {
            selectedRadiobut = radioMale.getText().toString();
        } else if (radioFemale.isChecked()) {
            selectedRadiobut = radioFemale.getText().toString();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);

        if (sp.contains("height") && sp.contains("weight") && sp.contains("age") && sp.contains("gender")) {
            // Values are stored, navigate to MainMenu directly
            Intent intent = new Intent(MainActivity.this, MainMenu.class);
            startActivity(intent);
        } else {
            radioMale = (RadioButton) findViewById(R.id.radioMale);
            radioFemale = (RadioButton) findViewById(R.id.radioFemale);
            button = (Button) findViewById(R.id.button);
            enterHeight = (EditText) findViewById(R.id.enterHeight);
            enterWeight = (EditText) findViewById(R.id.enterWeight);
            enterAge = (EditText) findViewById(R.id.enterAge);
            radioGroup = (RadioGroup) findViewById(R.id.radioSex);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (heightIsValid() & weightIsValid() & ageIsValid() & genderIsSelected()) {

                        height = Double.valueOf(enterHeight.getText().toString());
                        weight = Double.valueOf(enterWeight.getText().toString());
                        age = Double.valueOf(enterAge.getText().toString());

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("height", String.valueOf(Integer.valueOf((int) height)));
                        editor.putString("weight", String.valueOf(Integer.valueOf((int) weight)));
                        editor.putString("age", String.valueOf(Integer.valueOf((int) age)));
                        editor.putString("gender", String.valueOf(selectedRadiobut));
                        editor.commit();

                        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        Intent intent = new Intent(MainActivity.this, MainMenu.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (enterHeight != null) {
            enterHeight.setText("");
        }
        if (enterWeight != null) {
            enterWeight.setText("");
        }
        if (enterAge != null) {
            enterAge.setText("");
        }
        if (radioGroup != null) {
            radioGroup.clearCheck();
        }
    }
}
