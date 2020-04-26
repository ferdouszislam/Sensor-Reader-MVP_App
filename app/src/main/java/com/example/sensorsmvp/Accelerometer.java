package com.example.sensorsmvp;

import static java.lang.Math.abs;

public class Accelerometer extends MotionSensorSuper {

    private static final double MINIMUM_DIFFERENCE  = 0.01d;

    public Accelerometer(String sensorName) {
        super(sensorName);
    }

    public Accelerometer(String sensorName, double xValue, double yValue, double zValue, double timeStamp) {
        super(sensorName, xValue, yValue, zValue, timeStamp);
    }

    @Override
    public boolean isValid(float[] values) {

        if( abs(this.getxValue() - values[0]) >= MINIMUM_DIFFERENCE
                || abs(this.getyValue() - values[1]) >= MINIMUM_DIFFERENCE
                || abs(this.getzValue() - values[2]) >= MINIMUM_DIFFERENCE
        ){

            return true;

        }

        return false;

    }
}
