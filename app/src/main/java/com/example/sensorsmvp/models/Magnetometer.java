package com.example.sensorsmvp.models;

import android.hardware.SensorManager;

public class Magnetometer {

    private static final double MINIMUN_DIFFERENCE = 0.01d;

    // magnetic field strength
    private float[] magnetometerReadings = new float[3];
    // azimuth, pitch, roll
    private float[] orientationAngles = new float[3];
    private float[] prevOrientationAngles = new float[3];

    // used to obtain orientation angle
    private float[] rotationMatrix = new float[9];
    private float[] accelerometerReadings = new float[3];
    private boolean accelerometerReadingsSet, magnetometerReadingSet;

    // timeStamp
    private double timeStamp;

    private static double startTime = -1;
    private static final double nanoToS = 1.0d/1000000000.0d;

    public Magnetometer() {

        // isValid() returns true the first time
        prevOrientationAngles[0] = 99;
        prevOrientationAngles[1] = 99;
        prevOrientationAngles[2] = 99;

        // initially no readings available
        accelerometerReadingsSet = false;
        magnetometerReadingSet = false;

    }

    public void setAccelerometerReadings(float[] accelerometerReadings){

        this.accelerometerReadings[0] = accelerometerReadings[0];
        this.accelerometerReadings[1] = accelerometerReadings[1];
        this.accelerometerReadings[2] = accelerometerReadings[2];

        this.accelerometerReadingsSet = true;
    }

    public void setMagnetometerReadings(float[] magnetometerReadings){

        this.magnetometerReadings[0] = magnetometerReadings[0];
        this.magnetometerReadings[1] = magnetometerReadings[1];
        this.magnetometerReadings[2] = magnetometerReadings[2];

        this.magnetometerReadingSet = true;
    }

    public void computeOrientationAngles(){

        if(magnetometerReadingSet && accelerometerReadingsSet) {
            // update rotation matrix, which is needed to compute orientation angles
            SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReadings, magnetometerReadings);

            // get orientation angles
            SensorManager.getOrientation(rotationMatrix, orientationAngles);
        }

    }

    public boolean isValid(){

        for(int i=0;i<3;i++) {
            if (Math.abs(prevOrientationAngles[0] - orientationAngles[0]) >= MINIMUN_DIFFERENCE
                    || Math.abs(prevOrientationAngles[1] - orientationAngles[1]) >= MINIMUN_DIFFERENCE
                    || Math.abs(prevOrientationAngles[2] - orientationAngles[2]) >= MINIMUN_DIFFERENCE
                    && accelerometerReadingsSet && magnetometerReadingSet
            ) return true;
        }

        return false;

    }


    public double getAzimuth() {

        double azimuth = Math.toDegrees(orientationAngles[0]);

        if(azimuth<0)
            azimuth +=360;

        return azimuth;
    }

    public double getPitch() {
        return Math.toDegrees(orientationAngles[1]);
    }

    public double getRoll() {
        return Math.toDegrees(orientationAngles[2]);
    }

    public double getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {

        if(startTime==-1){
            startTime = (double) timeStamp*nanoToS;
        }

        this.timeStamp = (double) timeStamp*nanoToS - startTime;
    }

    public static void setStartTime(long startTime) {
        Magnetometer.startTime = (double) startTime*nanoToS;
    }

    @Override
    public String toString() {
        return "Magnetometer:\n" +
                "azimuth = " +Math.floor( this.getAzimuth()*100)/100.00d+
                ", pitch = " +Math.floor( Math.toDegrees(orientationAngles[1])*100)/100.00d+
                ", roll = " +Math.floor( Math.toDegrees(orientationAngles[2])*100)/100.00d+
                ", timeStamp=" + Math.floor(timeStamp*100)/100.00d;
    }
}
