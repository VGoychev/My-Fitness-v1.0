package com.example.mystepscounter;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mystepscounter.macros_calculator_package.FoodAdapter;
import com.example.mystepscounter.macros_calculator_package.FoodItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MacrosCalculator extends AppCompatActivity {
    Spinner spinner;
    Button buttonAdd;
    RecyclerView recycler_food_items;
    TextView calculatedValues;
    List<FoodItem> foodItemList = new ArrayList<>();
    FoodAdapter foodAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_macros_calculator);
        recycler_food_items = findViewById(R.id.recycler_food_items);
        spinner = findViewById(R.id.spinner);
        buttonAdd = findViewById(R.id.buttonAdd);
        calculatedValues = findViewById(R.id.calculatedValues);
        SharedPreferences sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        double height = Double.parseDouble(sp.getString("height", ""));
        double weight = Double.parseDouble(sp.getString("weight", ""));
        double age = Double.parseDouble(sp.getString("age", ""));
        String gender = sp.getString("gender", "");
        final double MALE_CONST = 88.362;
        final double MALE_WEIGHT_MULT = 13.397;
        final double MALE_HEIGHT_MULT = 4.799;
        final double MALE_AGE_MULT = 5.677;
        final double FEMALE_CONST = 447.593;
        final double FEMALE_WEIGHT_MULT = 9.247;
        final double FEMALE_HEIGHT_MULT = 3.098;
        final double FEMALE_AGE_MULT = 4.330;
        double calculatedBMR = 0;
        if (gender.equalsIgnoreCase("male")) {
            calculatedBMR = MALE_CONST + (MALE_WEIGHT_MULT * weight) + (MALE_HEIGHT_MULT * height) - (MALE_AGE_MULT * age);
        } else if (gender.equalsIgnoreCase("female")) {
            calculatedBMR = FEMALE_CONST + (FEMALE_WEIGHT_MULT * weight) + (FEMALE_HEIGHT_MULT * height) - (FEMALE_AGE_MULT * age);
        }
        double activityLevelMultiplier = 1.3;
        double caloriesForMaintenance = calculatedBMR * activityLevelMultiplier;
        double caloriesForWeightGain = caloriesForMaintenance + 300;
        double caloriesForWeightLoss = caloriesForMaintenance - 300;
        TextView maintenanceCaloriesTextView = findViewById(R.id.maintenanceCaloriesTextView);
        TextView weightGainCaloriesTextView = findViewById(R.id.weightGainCaloriesTextView);
        TextView weightLossCaloriesTextView = findViewById(R.id.weightLossCaloriesTextView);
        maintenanceCaloriesTextView.setText("To maintain the weight: " + String.format("%.2f", caloriesForMaintenance) + " calories/day");
        weightGainCaloriesTextView.setText("To gain weight: " + String.format("%.2f", caloriesForWeightGain) + " calories/day");
        weightLossCaloriesTextView.setText("To lose weight: " + String.format("%.2f", caloriesForWeightLoss) + " calories/day");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_menu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        foodAdapter = new FoodAdapter(this, foodItemList);
        recycler_food_items.setAdapter(foodAdapter);
        recycler_food_items.setLayoutManager(new LinearLayoutManager(this));
        final String[] selectedSpinnerItem = {""};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedSpinnerItem[0] = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(MacrosCalculator.this, "Selected: " + selectedSpinnerItem[0], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItem = selectedSpinnerItem[0];
                int calorie = 0;
                int fat = 0;
                int carbohydrate = 0;
                int protein = 0;
                if (selectedItem.equals("Chicken with rice(350g)")) {
                    calorie = 510;
                    fat = 17;
                    carbohydrate = 21;
                    protein = 65;
                } else if (selectedItem.equals("Chicken with potatoes(350g)")) {
                    calorie = 250;
                    fat = 6;
                    carbohydrate = 30;
                    protein = 20;
                } else if (selectedItem.equals("Pork with rice(350g)")) {
                    calorie = 497;
                    fat = 12;
                    carbohydrate = 54;
                    protein = 39;
                } else if (selectedItem.equals("Pork with potatoes(350g)")) {
                    calorie = 409;
                    fat = 14;
                    carbohydrate = 42;
                    protein = 28;
                } else if (selectedItem.equals("Pasta(250g)")) {
                    calorie = 392;
                    fat = 2;
                    carbohydrate = 76;
                    protein = 14;
                } else if (selectedItem.equals("Pizza(400g)")) {
                    calorie = 1054;
                    fat = 37;
                    carbohydrate = 133;
                    protein = 44;
                } else if (selectedItem.equals("Low-calorie pizza(400g)")) {
                    calorie = 181;
                    fat = 4;
                    carbohydrate = 30;
                    protein = 11;
                } else if (selectedItem.equals("Beef Burger(300g)")) {
                    calorie = 732;
                    fat = 36;
                    carbohydrate = 53;
                    protein = 45;
                } else if (selectedItem.equals("Low-calorie Burger(300g)")) {
                    calorie = 559;
                    fat = 25;
                    carbohydrate = 3;
                    protein = 80;
                } else if (selectedItem.equals("Scrambled eggs(250g)")) {
                    calorie = 373;
                    fat = 27;
                    carbohydrate = 4;
                    protein = 25;
                } else if (selectedItem.equals("Banana(118g)")) {
                    calorie = 105;
                    fat = 0;
                    carbohydrate = 27;
                    protein = 1;
                } else if (selectedItem.equals("Apple(182g)")) {
                    calorie = 95;
                    fat = 0;
                    carbohydrate = 25;
                    protein = 1;
                } else if (selectedItem.equals("Pitted Dates(30g)")) {
                    calorie = 90;
                    fat = 0;
                    carbohydrate = 20;
                    protein = 1;
                } else if (selectedItem.equals("Coca-Cola(500ml)")) {
                    calorie = 200;
                    fat = 0;
                    carbohydrate = 55;
                    protein = 0;
                } else if (selectedItem.equals("Coca-Cola Zero(500ml)")) {
                    calorie = 0;
                    fat = 0;
                    carbohydrate = 0;
                    protein = 0;
                }
                FoodItem newItem = new FoodItem(selectedItem, calorie, fat, carbohydrate, protein);
                foodItemList.add(newItem);
                foodAdapter.notifyItemInserted(foodItemList.size() - 1);
                updateCalculatedValues();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback());
        itemTouchHelper.attachToRecyclerView(recycler_food_items);
        checkAndResetData();
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(foodItemList);
        editor.putString("foodItemList", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("foodItemList", null);
        Type type = new TypeToken<ArrayList<FoodItem>>() {}.getType();
        foodItemList = gson.fromJson(json, type);
        if (foodItemList == null) {
            foodItemList = new ArrayList<>();
        }
        foodAdapter = new FoodAdapter(this, foodItemList);
        recycler_food_items.setAdapter(foodAdapter);
        recycler_food_items.setLayoutManager(new LinearLayoutManager(this));
        updateCalculatedValues();
    }
    private void updateCalculatedValues() {
        int totalCalorie = 0;
        int totalFat = 0;
        int totalCarbohydrate = 0;
        int totalProtein = 0;
        for (FoodItem foodItem : foodItemList) {
            totalCalorie += foodItem.getCalorie();
            totalFat += foodItem.getFat();
            totalCarbohydrate += foodItem.getCarbohydrate();
            totalProtein += foodItem.getProtein();
        }
        String totalText = "Total - " + totalCalorie + " kcal, "
                + totalFat + "g fat, "
                + totalCarbohydrate + "g carbs, "
                + totalProtein + "g protein";
        calculatedValues.setText(totalText);
    }
    private class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        SwipeToDeleteCallback() {
            super(0, ItemTouchHelper.LEFT);// Set swipe directions here
        }
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            foodItemList.remove(position);
            foodAdapter.notifyItemRemoved(position);
            updateCalculatedValues();
        }
    }
    private void checkAndResetData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyUserPrefs", MODE_PRIVATE);
        String savedDate = sharedPreferences.getString("lastSavedDate", "");
        Calendar calendar = Calendar.getInstance();
        String currentDate = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (!currentDate.equals(savedDate)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("lastSavedDate", currentDate);
            editor.remove("foodItemList");
            editor.apply();
            foodItemList.clear();
            foodAdapter.notifyDataSetChanged();
            calculatedValues.setText("Total -");
        }
    }
}