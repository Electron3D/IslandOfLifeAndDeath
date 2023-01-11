package com.electron3d.model.animals.predators;

import com.electron3d.model.Animal;
import com.electron3d.model.animals.Predatory;
import com.electron3d.model.island.Field;

public class Wolf extends Animal implements Predatory {
    public Wolf(double weight, int speed, double amountOfFoodToBeFull, Field location) {
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

    @Override
    public void hunt() {

    }
}
