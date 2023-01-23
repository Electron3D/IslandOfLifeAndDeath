package com.electron3d.model.creatures;

public final class PlantProperties {
    private final String type;
    private final double weight;
    private final int boundOnTheSameField;
    public PlantProperties(String type, double weight, int boundOnTheSameField) {
        this.type = type;
        this.weight = weight;
        this.boundOnTheSameField = boundOnTheSameField;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public int getBoundOnTheSameField() {
        return boundOnTheSameField;
    }
}
