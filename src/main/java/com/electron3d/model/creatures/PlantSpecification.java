package com.electron3d.model.creatures;

public final class PlantSpecification {
    private final String type;
    private final double weight;
    private final int boundOnTheSameField;
    public final static String ICON = "\uD83C\uDF3F";
    public PlantSpecification(String type, double weight, int boundOnTheSameField) {
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

    public int getBoundOnTheSameCell() {
        return boundOnTheSameField;
    }
}
