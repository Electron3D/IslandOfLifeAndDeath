package com.electron3d.model;

import com.electron3d.model.island.Field;

import java.util.Random;

public class Plant {
    public static final int BOUND_ON_THE_SAME_FIELD = 200;
    private final Field location;

    public Plant(Field location) {
        this.location = location;
    }

    public boolean grow() {
        int amount = location.getPlantsOnThisField();
        if (amount > 0 && amount < BOUND_ON_THE_SAME_FIELD) {
            Random random = new Random();
            int newPlantsNum = random.nextInt(0, 2);
            if (newPlantsNum == 0) {
                return false;
            }
            location.setPlantsOnThisField(amount + newPlantsNum);
            return true;
        } else {
            return false;
        }
    }

    public Field getLocation() {
        return location;
    }
}
