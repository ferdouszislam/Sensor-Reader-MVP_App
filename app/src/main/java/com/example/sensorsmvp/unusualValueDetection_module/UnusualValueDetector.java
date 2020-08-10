package com.example.sensorsmvp.unusualValueDetection_module;

public class UnusualValueDetector {

    // object responsible for calculating standard deviation and mean
    private StandardDeviationMean standardDeviationMean;
    // state of last received value/sample
    private boolean isValueUnusual = false;

    // responds to unusual value detection
    private unusualValueResponse unusualValueResponse=null;

    public UnusualValueDetector(StandardDeviationMean standardDeviationMean) {
        this.standardDeviationMean = standardDeviationMean;
    }

    public UnusualValueDetector(StandardDeviationMean standardDeviationMean,
                                UnusualValueDetector.unusualValueResponse unusualValueResponse
    ) {
        this.standardDeviationMean = standardDeviationMean;
        this.unusualValueResponse = unusualValueResponse;
    }

    public void analyzeMotionSensorData(double x, double y, double z){

        double resultant = Math.sqrt(x*x + y*y + z*z);

        standardDeviationMean.addSample(resultant);


        if(     (// X > (Mean + StandardDeviation)
                isGreater(standardDeviationMean.getX(), standardDeviationMean.getMean()+standardDeviationMean.getStandardDeviation())

                ||

                // X < (Mean - StandardDeviation)
                isGreater(standardDeviationMean.getMean()-standardDeviationMean.getStandardDeviation(), standardDeviationMean.getX()) )

                   // got more than 30 data
                && standardDeviationMean.getNumberOfData()>=30
        ) {

            isValueUnusual = true;

            if(unusualValueResponse!=null)
                unusualValueResponse.unusualValueDetected("unusual value = "+resultant);

        }

        else
            isValueUnusual = false;
    }

    private boolean isGreater(double a, double b){
        double EPS = 1e-9;
        return (a-b) > EPS; // true iff a>b
    }


    // getters & setters
    public StandardDeviationMean getStandardDeviationMean() {
        return standardDeviationMean;
    }

    public boolean isValueUnusual() {
        return isValueUnusual;
    }

    public void setUnusualValueResponse(UnusualValueDetector.unusualValueResponse unusualValueResponse) {
        this.unusualValueResponse = unusualValueResponse;
    }

    // implement this interface to respond to unusual value detection
    public interface unusualValueResponse{

        // invoked by the implementer when unusual value is detected
        void unusualValueDetected(String message);

    }
}