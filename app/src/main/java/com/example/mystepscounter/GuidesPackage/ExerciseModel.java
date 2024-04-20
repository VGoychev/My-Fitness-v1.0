package com.example.mystepscounter.GuidesPackage;

public class ExerciseModel {
    String exerciseName;
    String exerciseDescription;
    String exerciseInstruction;
    int  muscle_group_exercise_image;
    public ExerciseModel(String exerciseName, int muscle_group_exercise_image,
                         String exerciseDescription, String exerciseInstruction) {
        this.exerciseName = exerciseName;
        this.muscle_group_exercise_image = muscle_group_exercise_image;
        this.exerciseDescription = exerciseDescription;
        this.exerciseInstruction = exerciseInstruction;
    }
    public String getExerciseName() {
        return exerciseName;
    }
    public int getMuscle_group_exercise_image() {
        return muscle_group_exercise_image;
    }
    public String getExerciseDescription() {
        return exerciseDescription;
    }
    public String getExerciseInstruction() {
        return exerciseInstruction;
    }
}
