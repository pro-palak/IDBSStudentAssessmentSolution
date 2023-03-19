package com.idbs.devassessment.solution.model;

public class Terms {
    int power;
    int multiplier;
    String action;

    public Terms(int power, int multiplier, String action) {
        this.power = power;
        this.multiplier = multiplier;
        this.action = action;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}