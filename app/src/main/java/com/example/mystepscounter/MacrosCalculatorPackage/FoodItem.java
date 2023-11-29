package com.example.mystepscounter.MacrosCalculatorPackage;

public class FoodItem {
    String displayText;
    int calorie;
    int fat;
    int carbohydrate;
    int protein;

    public FoodItem(String displayText, int calorie, int fat, int carbohydrate, int protein) {
        this.displayText = displayText;
        this.calorie = calorie;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
    }
    public String getDisplayText() {
        return displayText;
    }


    public int getCalorie() {
        return calorie;
    }
    public int getFat(){
        return fat;
    }
    public int getCarbohydrate(){
        return carbohydrate;
    }
    public int getProtein(){
        return protein;
    }
}
