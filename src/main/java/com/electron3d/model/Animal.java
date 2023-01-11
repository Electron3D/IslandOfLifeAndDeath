package com.electron3d.model;

import com.electron3d.model.island.Field;

public abstract class Animal {
    private double weight;
    private int speed;
    private final double amountOfFoodToBeFull;
    private Field location;
    private static int boundOnTheSameField;

    public Animal(double weight, int speed, double amountOfFoodToBeFull, Field location) {
        this.weight = weight;
        this.speed = speed;
        this.amountOfFoodToBeFull = amountOfFoodToBeFull;
        this.location = location;
    }

    protected abstract void eat();

    protected abstract void breed();

    protected abstract void walk();

    public double getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    public double getAmountOfFoodToBeFull() {
        return amountOfFoodToBeFull;
    }

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }

    public static int getBoundOnTheSameField() {
        return boundOnTheSameField;
    }
}
