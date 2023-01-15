package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.Herbivores;
import com.electron3d.model.island.Field;

public class Sheep extends Animal implements Herbivores, Eatable {
    private static double WEIGHT;
    private static int BOUND_ON_THE_SAME_FIELD;
    private static int SPEED;
    private static double AMOUNT_OF_FOOD_TO_BE_FULL;

    public Sheep(Field location) {
        super(location);
    }

    @Override
    public double restoreHP() {
        return WEIGHT;
    }
}
