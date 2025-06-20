package com.example.authdemo.dto;

public class PostfixCalculationResult {
    private String postfixExpression;
    private double result;

    public PostfixCalculationResult(String postfixExpression, double result) {
        this.postfixExpression = postfixExpression;
        this.result = result;
    }

    // Getters and Setters
    public String getPostfixExpression() {
        return postfixExpression;
    }

    public void setPostfixExpression(String postfixExpression) {
        this.postfixExpression = postfixExpression;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
} 