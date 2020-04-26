package com.example.sensorsmvp;

import static java.lang.Math.abs;

public class Gyroscope extends MotionSensorSuper {

    private static final double MINIMUM_DIFFERENCE  = 0.001d;
    private static final double TOO_SMALL_VALUE  = 0.009d;

    public Gyroscope(String sensorName) {
        super(sensorName);
    }

    public Gyroscope(String sensorName, double xValue, double yValue, double zValue, double timeStamp) {
        super(sensorName, xValue, yValue, zValue, timeStamp);
    }

    @Override
    public boolean isValid(float[] values) {

        // set too small values to zero
        for(int i=0;i<3;i++){
            if(abs(values[i])<=TOO_SMALL_VALUE)
                values[i] = 0;
        }

        if( abs(this.getxValue() - values[0]) >= MINIMUM_DIFFERENCE
                ||  abs(this.getyValue() - values[1]) >= MINIMUM_DIFFERENCE
                || abs(this.getzValue() - values[2]) >= MINIMUM_DIFFERENCE
        ){

            return true;

        }

        return false;
    }
}
