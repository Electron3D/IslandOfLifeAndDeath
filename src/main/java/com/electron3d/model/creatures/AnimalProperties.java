package com.electron3d.model.creatures;

public final class AnimalProperties {
    private final String type;
    private final double weight;
    private final int range;
    private final int boundOnTheSameField;
    private final double amountOfFoodToBeFull;

    public AnimalProperties(String type, double weight, int range, int boundOnTheSameField, double amountOfFoodToBeFull) {
        this.type = type;
        this.weight = weight;
        this.range = range;
        this.boundOnTheSameField = boundOnTheSameField;
        this.amountOfFoodToBeFull = amountOfFoodToBeFull;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public int getRange() {
        return range;
    }

    public int getBoundOnTheSameField() {
        return boundOnTheSameField;
    }

    public double getAmountOfFoodToBeFull() {
        return amountOfFoodToBeFull;
    }
}
