package com.electron3d.model.animals.herbivores;

import com.electron3d.model.Animal;
import com.electron3d.model.animals.Herbivores;
import com.electron3d.model.island.Field;

public class Horse extends Animal implements Herbivores {
    public Horse(double weight, int speed, double amountOfFoodToBeFull, Field location) {
        super(weight, speed, amountOfFoodToBeFull, location);
    }

    @Override
    protected void eat() {

    }

    @Override
    protected void breed() {

    }

    @Override
    protected void walk() {

    }
}
