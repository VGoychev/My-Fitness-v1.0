package com.example.mystepscounter.GuidesPackage;

public class GuidesModel {
    String muscle_group_exercise_name;
    String muscle_group_description;
    int image_muscle_group;

    public GuidesModel(String muscle_group_exercise_name, int image_muscle_group,
                       String muscle_group_description) {
        this.muscle_group_exercise_name = muscle_group_exercise_name;
        this.image_muscle_group = image_muscle_group;
        this.muscle_group_description = muscle_group_description;
    }
    public String getMuscle_group_exercise_name() {
        return muscle_group_exercise_name;
    }
    public int getImage_muscle_group() {
        return image_muscle_group;
    }
    public String getMuscle_group_description() {
        return muscle_group_description;
    }
}
