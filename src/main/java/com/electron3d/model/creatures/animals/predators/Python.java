package com.electron3d.model.creatures.animals.predators;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.Predatory;
import com.electron3d.model.island.Field;

public class Python  extends Animal implements Predatory, Eatable {
    private static double WEIGHT;
    private static int BOUND_ON_THE_SAME_FIELD;
    private static int SPEED;
    private static double AMOUNT_OF_FOOD_TO_BE_FULL;

    public Python(Field location) {
        super(location);
    }

    @Override
    public double restoreHP() {
        return WEIGHT;
    }
}
