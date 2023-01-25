package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.*;
import com.electron3d.model.island.Cell;

import java.util.List;
import java.util.Random;

public abstract class HerbivoresAnimal extends Animal implements Herbivores {
    public HerbivoresAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public Eatable findFood(List<Eatable> foodList) {
        return foodList.parallelStream()
                .filter(x -> x instanceof Plant)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean eat(Eatable food) {
        double restoredHP = eatPlant((Plant) food);
        if (restoredHP == 0) {
            return false;
        } else {
            currentHealthPoints = currentHealthPoints + (int) restoredHP;
            return true;
        }
    }

    @Override
    public double eatPlant(Plant food) {
        Random chanceToEat = new Random();
        double chance = getProperties().getChancesToEat(food.getProperties().getType());
        double restoredHP = 0;
        if (chanceToEat.nextDouble(0, 1) < chance) {
            restoredHP = food.restoreHP();
        }
        if (restoredHP <= getProperties().getAmountOfFoodToBeFull()) {
            if (currentHealthPoints + restoredHP <= startedHealthPoints) {
                return restoredHP;
            } else {
                return startedHealthPoints - currentHealthPoints;
            }
        } else {
            return getProperties().getAmountOfFoodToBeFull();
        }
    }
}
