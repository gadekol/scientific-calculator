package com.example.calculator.controller;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        double result = 0;
        String op = request.getOperation();
        double operand1 = request.getOperand1();
        double operand2 = request.getOperand2();

        try {
            switch (op) {
                case "add":
                    result = operand1 + operand2;
                    break;
                case "sub":
                    result = operand1 - operand2;
                    break;
                case "mul":
                    result = operand1 * operand2;
                    break;
                case "div":
                    if (operand2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = operand1 / operand2;
                    break;
                case "sin":
                    // Expect operand1 to be in degrees; convert to radians
                    result = Math.sin(Math.toRadians(operand1));
                    break;
                case "cos":
                    result = Math.cos(Math.toRadians(operand1));
                    break;
                case "tan":
                    result = Math.tan(Math.toRadians(operand1));
                    break;
                case "log":
                    if (operand1 <= 0) {
                        throw new ArithmeticException("Logarithm of non-positive number");
                    }
                    result = Math.log10(operand1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                                 .body(new CalculationResponse(Double.NaN, e.getMessage()));
        }
        return ResponseEntity.ok(new CalculationResponse(result));
    }
}

