package com.example.calculator.model;

public class CalculationRequest {
    private String operation;
    private double operand1;
    private double operand2; // For unary operations, this may be ignored

    public CalculationRequest() {}

    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public double getOperand1() {
        return operand1;
    }
    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }
    public double getOperand2() {
        return operand2;
    }
    public void setOperand2(double operand2) {
        this.operand2 = operand2;
    }
}

