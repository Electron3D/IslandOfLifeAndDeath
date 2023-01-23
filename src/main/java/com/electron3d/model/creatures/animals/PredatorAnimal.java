package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.island.Cell;

import java.util.List;
import java.util.Random;

public abstract class PredatorAnimal extends Animal implements Predatory {
    public PredatorAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public Eatable findFood(List<Eatable> foodList) {
        return foodList.parallelStream().filter(x -> x instanceof Animal).findFirst().orElse(null);
    }

    @Override
    public boolean eat(Eatable food) {
        double restoredHP = hunt((Animal) food);
        if (restoredHP == 0) {
            return false;
        } else {
            currentHealthPoints = currentHealthPoints + (int) restoredHP;
            return true;
        }
    }

    @Override
    public double hunt(Animal food) {
        Random chanceToEat = new Random();
        Eatable exactFood = (Eatable) food;
        double chance = getProperties().getChancesToEat(food.getProperties().getType());
        double restoredHP = 0;
        if (chanceToEat.nextDouble(0, 1) < chance) {
            restoredHP = exactFood.restoreHP();
            getCurrentLocation().deleteAnimal(food);
            if (restoredHP <= getProperties().getAmountOfFoodToBeFull()) {
                if (currentHealthPoints + restoredHP <= startedHealthPoints) {
                    return restoredHP;
                } else {
                    return startedHealthPoints - currentHealthPoints;
                }
            } else {
                return getProperties().getAmountOfFoodToBeFull();
            }
        } else {
            return restoredHP;
        }
    }
}
