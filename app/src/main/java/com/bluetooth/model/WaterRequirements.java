package com.bluetooth.model;

public enum WaterRequirements {

    LITTLE("1"), MEDIUM("2"), MUCH("3");

    private String valueToSend;

    private WaterRequirements(String valueToSend) {

        this.valueToSend = valueToSend;
    }

    public String getValueToSend() {

        return valueToSend;
    }
}
