package com.electron3d.model;

public abstract class Animal {
    private double weight;
    private int speed;
    private double amountOfFoodToBeFull;
    private int maxNumberOnTheSameField;
    protected abstract void eat();
    protected abstract void breed();
    protected abstract void walk();

}
