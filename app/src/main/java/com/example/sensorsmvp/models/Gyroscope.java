package com.example.sensorsmvp.models;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class Gyroscope extends MotionSensorSuper {

    private static final double MINIMUM_DIFFERENCE  = 0.001d;
    private static final double TOO_SMALL_VALUE  = 0.009d;

    private double horizontalAngle;

    // need acceleration data for horizontal angle calculation
    private float[] accelerometerReadings = new float[3];
    private boolean accelerationReadingsTaken = false;

    public Gyroscope(String sensorName) {
        super(sensorName);

        horizontalAngle = 0;
    }

    public Gyroscope(String sensorName, double xValue, double yValue, double zValue, double timeStamp) {
        super(sensorName, xValue, yValue, zValue, timeStamp);
    }

    public void setAccelerometerReadings(float[] accelerometerReadings) {
        this.accelerometerReadings[0] = accelerometerReadings[0];
        this.accelerometerReadings[1] = accelerometerReadings[1];
        this.accelerometerReadings[2] = accelerometerReadings[2];

        accelerationReadingsTaken = true;
    }

    public void calculateHorizontalAngle(long timeStamp){

        double timeDuration;

        if(startTime!=-1) {

            timeDuration = ( (double) timeStamp*super.nanoToS - startTime ) - this.timeStamp;
        }
        else
            return;

        double rotationSpeed = -1;

        if(accelerometerReadings[0]>=8.5f){
            // x axis is the axis of rotation

            rotationSpeed = (-1)*xValue;

        }

        else if(accelerometerReadings[1]>=8.5f){
            // y axis is the axis of rotation

            rotationSpeed = (-1)*yValue;

        }

        else if(accelerometerReadings[2]>=8.5f){
            // z axis is the axis of rotation

            rotationSpeed = (-1)*zValue;

        }

        if(rotationSpeed==-1) {
            horizontalAngle = 0;
            return;
        }

        rotationSpeed = Math.toDegrees(rotationSpeed);

        horizontalAngle += (rotationSpeed*timeDuration);

        if(horizontalAngle>=360)
            horizontalAngle -= 360;
        if(horizontalAngle<0)
            horizontalAngle = 360+horizontalAngle;

    }

    @Override
    public boolean isValid(float[] values) {

        // set too small values to zero
        for(int i=0;i<3;i++){
            if(abs(values[i])<=TOO_SMALL_VALUE)
                values[i] = 0;
        }

        if( abs(this.getxValue() - values[0]) >= MINIMUM_DIFFERENCE
                || abs(this.getyValue() - values[1]) >= MINIMUM_DIFFERENCE
                || abs(this.getzValue() - values[2]) >= MINIMUM_DIFFERENCE
                && accelerationReadingsTaken
        ){

            return true;

        }

        return false;
    }

    public double getHorizontalAngle() {
        return horizontalAngle;
    }

    @Override
    public String toString(){

        return  sensorName + ":\n" +
                "xValue = " + ( floor(xValue*100)/100.00d ) +
                ", yValue = " + ( floor(yValue*100)/100.00d ) +
                ", zValue = " + ( floor(zValue*100)/100.00d ) +
                " \n horizontal Angle = "+ (floor(horizontalAngle*100)/100.00d) +" degrees"+
                "\ntimeStamp = " +(floor(timeStamp*100)/100.00d) +" seconds";

    }

}
