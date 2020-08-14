package com.example.sensorsmvp.unusualValueDetection_module;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.LinkedList;

public class StandardDeviationMean {

    // need to keep track of all values for standard deviation calculation
    private LinkedList<Double> Xs = new LinkedList<>();

    // necessary for calculating mean & standard deviation
    private double sumOfAllData = 0, numberOfData = 0, sumOfXMinusMeanWholeSquare = 0;

    // sample, mean, standard deviation
    private double X = 0, mean = 0, standardDeviation = 0;

    public StandardDeviationMean() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addSample(double X){
        // update the mean & standard deviation with the new sample

        this.X = X;

        // linked list size is not limited (or at least 10^9, which is large enough)
        Xs.add(X);

        // mean calculation
        sumOfAllData = 0;
        Xs.forEach( (x) -> sumOfAllData+=x );
        numberOfData = Xs.size();
        mean = sumOfAllData/numberOfData;

        // standard deviation calculation
        sumOfXMinusMeanWholeSquare = 0;
        Xs.forEach( (x) -> sumOfXMinusMeanWholeSquare += (x-mean)*(x-mean) ); // have to do this every time mean changes
        standardDeviation = Math.sqrt(sumOfXMinusMeanWholeSquare/numberOfData);

    }


    public double getSumOfAllData() {
        return sumOfAllData;
    }

    public double getNumberOfData() {
        return numberOfData;
    }

    public double getSumOfXMinusMeanWholeSquare() {
        return sumOfXMinusMeanWholeSquare;
    }

    public double getX() {
        return X;
    }

    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }
}