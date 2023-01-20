package com.electron3d.model.creatures;

public class PlantProperties {
    private final double weight;
    private final int boundOnTheSameField;

    public PlantProperties(double weight, int boundOnTheSameField) {
        this.weight = weight;
        this.boundOnTheSameField = boundOnTheSameField;
    }

    public double getWeight() {
        return weight;
    }

    public int getBoundOnTheSameField() {
        return boundOnTheSameField;
    }
}
