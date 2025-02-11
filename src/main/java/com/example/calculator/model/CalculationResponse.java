package com.example.calculator.model;

public class CalculationResponse {
    private double result;
    private String error;

    public CalculationResponse() {}

    public CalculationResponse(double result) {
        this.result = result;
        this.error = null;
    }
    
    public CalculationResponse(double result, String error) {
        this.result = result;
        this.error = error;
    }

    public double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}

