package com.electron3d.model;

import com.electron3d.model.island.Field;

public abstract class Animal {
    private double weight;
    private int speed;
    private double amountOfFoodToBeFull;
    private Field location;
    private int boundOnTheSameField;

    protected abstract void eat();

    protected abstract void breed();
    protected abstract void walk();
    public int getBoundOnTheSameField() {
        return boundOnTheSameField;
    }

}
