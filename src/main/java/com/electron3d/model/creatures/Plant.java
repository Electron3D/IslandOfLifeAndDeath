package com.electron3d.model.creatures;

import com.electron3d.model.island.Field;

import java.util.Random;

public class Plant implements Eatable {
    public static final int BOUND_ON_THE_SAME_FIELD;
    private static final double WEIGHT;
    private final Field location;

    //todo config initializer
    static {
        BOUND_ON_THE_SAME_FIELD = 200;
        WEIGHT = 1;
    }

    public Plant(Field location) {
        this.location = location;
    }

    public boolean grow() {
        int amount = location.getAmountOfPlantsOnTheField();
        if (amount > 0 && amount < BOUND_ON_THE_SAME_FIELD) {
            Random random = new Random();
            int newPlantsNum = random.nextInt(0, 2);
            if (newPlantsNum == 0) {
                return false;
            }
            location.setAmountOfPlantsOnTheField(amount + newPlantsNum);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double restoreHP() {
        return WEIGHT;
    }

    public Field getLocation() {
        return location;
    }
}
