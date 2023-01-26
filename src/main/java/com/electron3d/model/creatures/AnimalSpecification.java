package com.electron3d.model.creatures;

import java.util.Map;

public final class AnimalSpecification {
    private final AnimalType type;
    private final double weight;
    private final int range;
    private final int boundOnTheSameField;
    private final double amountOfFoodToBeFull;
    private final Map<String, Double> chancesToEat;

    public AnimalSpecification(AnimalType type, double weight, int range,
                               int boundOnTheSameField, double amountOfFoodToBeFull,
                               Map<String, Double> chancesToEat) {
        this.type = type;
        this.weight = weight;
        this.range = range;
        this.boundOnTheSameField = boundOnTheSameField;
        this.amountOfFoodToBeFull = amountOfFoodToBeFull;
        this.chancesToEat = chancesToEat;
    }

    public AnimalType getType() {
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

    public Double getChancesToEat(String type) {
        return chancesToEat.get(type);
    }
}
