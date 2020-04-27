package com.example.sensorsmvp.models;

import static java.lang.Math.floor;

public abstract class MotionSensorSuper {

    protected String sensorName;

    protected double xValue, yValue, zValue;
    protected double timeStamp;

    private int accuracy;
    private double power;
    private double resolution;
    private double maxRange;
    private int minDelay;

    protected static final double nanoToS = 1.0d/1000000000.0d;
    protected static double startTime = -1;

    public MotionSensorSuper(String sensorName) {
        this.sensorName = sensorName;
    }

    public MotionSensorSuper(String sensorName, double xValue, double yValue, double zValue, double timeStamp) {
        this.sensorName = sensorName;

        this.xValue = xValue;
        this.yValue = yValue;
        this.zValue = zValue;
        this.timeStamp = timeStamp;
    }

    public abstract boolean isValid(float[] values);


    public double getxValue() {
        return xValue;
    }

    public void setxValue(double xValue) {
        this.xValue = xValue;
    }

    public double getyValue() {
        return yValue;
    }

    public void setyValue(double yValue) {
        this.yValue = yValue;
    }

    public double getzValue() {
        return zValue;
    }

    public void setzValue(double zValue) {
        this.zValue = zValue;
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

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public double getMaxRange() {
        return maxRange;
    }

    public void setMaxRange(double maxRange) {
        this.maxRange = maxRange;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public void setMinDelay(int minDelay) {
        this.minDelay = minDelay;
    }

    public static double getStartTime() {
        return startTime;
    }

    public static void setStartTime(long startTime) {
        MotionSensorSuper.startTime = (double) startTime*nanoToS;
    }

    public String getFullDescription(){

        return sensorName+": \n"+
                "power = "+power+" mA"+
                ", resolution "+resolution+
                ", max range = "+maxRange+
                ", min delay = "+minDelay+ "micro seconds";

    }

    @Override
    public String toString() {
        return  sensorName + ":\n" +
                "xValue = " + ( floor(xValue*100)/100.00d ) +
                ", yValue = " + ( floor(yValue*100)/100.00d ) +
                ", zValue = " + ( floor(zValue*100)/100.00d ) +
                "\ntimeStamp = " +(floor(timeStamp*100)/100.00d) +" seconds";
    }

}
