package com.example.mystepscounter;


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class StepCounter extends AppCompatActivity implements SensorEventListener {

    private TextView stepsView, caloriesBurned, steps;
    private TextView distanceCovered;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int stepsCount = 0;
    private static final double DEFAULT_STEP_LENGTH_M = 0.76; // Average step length in meters for adults
    private double stepLengthInMeters = DEFAULT_STEP_LENGTH_M; // Initialize with the default value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_counter);
        Bundle extras = getIntent().getExtras();
        stepsView = findViewById(R.id.stepsView);
        double height = extras.getDouble("height");
        double weight = extras.getDouble("weight");
        double age = extras.getDouble("age");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        this.caloriesBurned = findViewById(R.id.caloriesBurned);
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
//                    MY_PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION);
//            // Permission is not granted
//        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepsCount = (int) event.values[0];
            updateStepCountText();
            updateDistanceCoveredText();
        }
    }
    private void updateDistanceCoveredText() {
        double distanceInMeters = stepsCount * stepLengthInMeters;
        double distanceInKilometers = distanceInMeters / 1000.0;
        String distanceText = String.format("%.2f km", distanceInKilometers);
        distanceCovered.setText(distanceText);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void updateStepCountText() {
        stepsView.setText(String.valueOf(stepsCount));
    }
}

