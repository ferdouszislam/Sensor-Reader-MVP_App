package com.example.sensorsmvp.presenter;

import android.hardware.Sensor;

import com.example.sensorsmvp.models.Accelerometer;
import com.example.sensorsmvp.models.Gyroscope;
import com.example.sensorsmvp.models.Magnetometer;

public class SensorActivityPresenter {

    // models
    private Accelerometer accelerometer, gravity, linearAcceleration;
    private Gyroscope gyroscope;
    private Magnetometer magnetometer;

    // view
    private SensorView sensorView;

    public SensorActivityPresenter(SensorView view){

        // initialize models
        this.accelerometer = new Accelerometer("Accelerometer");
        this.gravity = new Accelerometer("Gravity Sensor");
        this.linearAcceleration = new Accelerometer("Linear Accelerometer");
        this.gyroscope = new Gyroscope("Gyroscope");
        this.magnetometer = new Magnetometer();

        // get reference to view
        this.sensorView = view;
    }

    public void updateSensorValues(int sensorType ,float[] values, long timeStamp){


        switch (sensorType){

            case Sensor.TYPE_ACCELEROMETER:

                if(accelerometer.isValid(values)){

                    accelerometer.setxValue(values[0]);
                    accelerometer.setyValue(values[1]);
                    accelerometer.setzValue(values[2]);

                    accelerometer.setTimeStamp(timeStamp);

                    // update UI
                    sensorView.updateAccelerometerDataText(accelerometer.toString());
                }

                // accelerometer data needed for magnetometer orientation angle calculation
                magnetometer.setAccelerometerReadings(values);

                // accelerometer data needed for horizontal angle calculation
                gyroscope.setAccelerometerReadings(values);

                break;

            case Sensor.TYPE_GRAVITY:

                if(gravity.isValid(values)){

                    gravity.setxValue(values[0]);
                    gravity.setyValue(values[1]);
                    gravity.setzValue(values[2]);

                    gravity.setTimeStamp(timeStamp);

                    // update UI
                    sensorView.updateGravityDataText(gravity.toString());
                }

                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:

                if(linearAcceleration.isValid(values)){

                    linearAcceleration.setxValue(values[0]);
                    linearAcceleration.setyValue(values[1]);
                    linearAcceleration.setzValue(values[2]);

                    linearAcceleration.setTimeStamp(timeStamp);

                    // update UI
                    sensorView.updateLinearAccelerationDataText(linearAcceleration.toString());
                }

                break;

            case Sensor.TYPE_GYROSCOPE:

                if(gyroscope.isValid(values)){

                    // calculate horizontal angle before setting new values
                    gyroscope.calculateHorizontalAngle(timeStamp);

                    gyroscope.setxValue(values[0]);
                    gyroscope.setyValue(values[1]);
                    gyroscope.setzValue(values[2]);

                    gyroscope.setTimeStamp(timeStamp);

                    // update UI
                    sensorView.updateGyroscopeDataText(gyroscope.toString());
                }

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:

                // set magnetometer values
                magnetometer.setMagnetometerReadings(values);

                // compute orientation angles
                magnetometer.computeOrientationAngles();

                if(magnetometer.isValid()) {
                    magnetometer.setTimeStamp(timeStamp);
                    sensorView.updateMagnetometerDataText(magnetometer.toString());
                }
                break;

        }

    }

    public void updateSensorAccuracy(int sensorType, int accuracy){

        switch (sensorType){

            case Sensor.TYPE_ACCELEROMETER:

                accelerometer.setAccuracy(accuracy);

                break;

            case  Sensor.TYPE_GYROSCOPE:

                gyroscope.setAccuracy(accuracy);

                break;

            case  Sensor.TYPE_GRAVITY:

                gravity.setAccuracy(accuracy);

                break;

            case  Sensor.TYPE_LINEAR_ACCELERATION:

                linearAcceleration.setAccuracy(accuracy);

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:

                magnetometer.setAccuracy(accuracy);

                break;

        }

    }

    public void updateSensorAvailability(int sensorType, Sensor sensor){

        switch (sensorType){

            case Sensor.TYPE_ACCELEROMETER:

                if(sensor==null) {
                    // update UI
                    sensorView.updateSensorUnavailableText(sensorType, "Accelerometer not available");
                    return;
                }
                // set descriptions of the sensor
                accelerometer.setPower(sensor.getPower());
                accelerometer.setResolution(sensor.getResolution());
                accelerometer.setMaxRange(sensor.getMaximumRange());
                accelerometer.setMinDelay(sensor.getMinDelay());

                break;

            case Sensor.TYPE_GYROSCOPE:

                if(sensor==null) {
                    // update UI
                    sensorView.updateSensorUnavailableText(sensorType, "Gyroscope not available");
                    return;
                }
                // set descriptions of the sensor
                gyroscope.setPower(sensor.getPower());
                gyroscope.setResolution(sensor.getResolution());
                gyroscope.setMaxRange(sensor.getMaximumRange());
                gyroscope.setMinDelay(sensor.getMinDelay());

                break;

            case Sensor.TYPE_GRAVITY:

                if(sensor==null) {
                    // update UI
                    sensorView.updateSensorUnavailableText(sensorType, "Gravity Sensor not available");
                    return;
                }
                // set descriptions of the sensor
                gravity.setPower(sensor.getPower());
                gravity.setResolution(sensor.getResolution());
                gravity.setMaxRange(sensor.getMaximumRange());
                gravity.setMinDelay(sensor.getMinDelay());

                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                if(sensor==null) {
                    // update UI
                    sensorView.updateSensorUnavailableText(sensorType, "Linear Acceleration Sensor not available");
                    return;
                }
                // set descriptions of the sensor
                linearAcceleration.setPower(sensor.getPower());
                linearAcceleration.setResolution(sensor.getResolution());
                linearAcceleration.setMaxRange(sensor.getMaximumRange());
                linearAcceleration.setMinDelay(sensor.getMinDelay());

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:

                if(sensor==null) {
                    // update UI
                    sensorView.updateSensorUnavailableText(sensorType, "Magnetometer not available");
                    return;
                }
                // set descriptions of the sensor
                magnetometer.setPower(sensor.getPower());
                magnetometer.setResolution(sensor.getResolution());
                magnetometer.setMaxRange(sensor.getMaximumRange());
                magnetometer.setMinDelay(sensor.getMinDelay());

                break;

        }


    }


    public interface SensorView{

        void updateAccelerometerDataText(String text);
        void updateGyroscopeDataText(String text);
        void updateMagnetometerDataText(String text);
        void updateGravityDataText(String text);
        void updateLinearAccelerationDataText(String text);

        void updateSensorUnavailableText(int sensorType, String text);
    }

}