package com.example.sensorsmvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorActivityPresenter.SensorView, SensorEventListener {

    // Presenter
    SensorActivityPresenter sensorActivityPresenter;

    // UI
    TextView accelerometerText, gyroscopeText, gravityText, linearAccelerationText, magnetometerText;

    // Sensors API configs
    private SensorManager sensorManager;
    private Sensor accelerometerSensor, gyroscopeSensor, gravitySensor, linearAccelerationSensor, magnetometerSensor;

    // debug log TAG
    private static final String TAG = "sensor-activity-debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorActivityPresenter = new SensorActivityPresenter(this);

        accelerometerText = findViewById(R.id.accelerometerText);
        gyroscopeText = findViewById(R.id.gyroscopeText);
        gravityText = findViewById(R.id.gravityText);
        linearAccelerationText = findViewById(R.id.linearAccelerationText);
        magnetometerText = findViewById(R.id.magnetometerText);

        setUpSensors();

    }

    private void setUpSensors() {

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorActivityPresenter.updateSensorAvailability(Sensor.TYPE_ACCELEROMETER, accelerometerSensor);
        sensorActivityPresenter.updateSensorAvailability(Sensor.TYPE_GYROSCOPE, gyroscopeSensor);
        sensorActivityPresenter.updateSensorAvailability(Sensor.TYPE_GRAVITY, gravitySensor);
        sensorActivityPresenter.updateSensorAvailability(Sensor.TYPE_LINEAR_ACCELERATION, linearAccelerationSensor);
        sensorActivityPresenter.updateSensorAvailability(Sensor.TYPE_MAGNETIC_FIELD, magnetometerSensor);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // start up sensors
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop sensors
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        sensorActivityPresenter.updateSensorValues(event.sensor.getType(), event.values, event.timestamp);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        sensorActivityPresenter.updateSensorAccuracy(sensor.getType(), accuracy);

    }


    @Override
    public void updateAccelerometerDataText(String text) {

        accelerometerText.setText(text);

    }

    @Override
    public void updateGyroscopeDataText(String text) {

        gyroscopeText.setText(text);

    }

    @Override
    public void updateMagnetometerDataText(String text) {

        magnetometerText.setText(text);

    }

    @Override
    public void updateGravityDataText(String text) {

        gravityText.setText(text);

    }

    @Override
    public void updateLinearAccelerationDataText(String text) {

        linearAccelerationText.setText(text);

    }

    @Override
    public void updateSensorUnavailableText(int sensorType, String text) {

        switch (sensorType){

            case Sensor.TYPE_ACCELEROMETER:
                accelerometerText.setText(text);
                break;

            case Sensor.TYPE_GYROSCOPE:
                gyroscopeText.setText(text);
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetometerText.setText(text);
                break;

            case Sensor.TYPE_GRAVITY:
                gravityText.setText(text);
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                linearAccelerationText.setText(text);
                break;


        }

    }
}