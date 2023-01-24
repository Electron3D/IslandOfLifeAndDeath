package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.creatures.animals.herbivores.Caterpillar;
import com.electron3d.model.island.Cell;

import java.util.List;
import java.util.Random;

public abstract class HerbivoresAndCaterpillarEatingAnimal extends HerbivoresAnimal implements Predatory {
    public HerbivoresAndCaterpillarEatingAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public Eatable findFood(List<Eatable> foodList) {
        return foodList.parallelStream()
                .filter(x -> x instanceof Plant)
                .findFirst()
                .orElse(findCaterpillar(foodList));
    }
    public Eatable findCaterpillar(List<Eatable> foodList) {
        return foodList.parallelStream()
                .filter(x -> x instanceof Caterpillar)
                .filter(y -> !((Caterpillar) y).isDead())
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean eat(Eatable food) {
        double restoredHP;
        if (food instanceof Plant plantToEat) {
            restoredHP = eatPlant(plantToEat);
        } else {
            Caterpillar dessert = (Caterpillar) food;
            restoredHP = hunt(dessert);
        }
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
        Caterpillar exactFood = (Caterpillar) food;
        double chance = getProperties().getChancesToEat(exactFood.getProperties().getType().getType());
        double restoredHP = 0;
        if (chanceToEat.nextDouble(0, 1) < chance) {
            restoredHP = exactFood.restoreHP();
            //System.out.println("Animal " + this + " eat " + food);
            exactFood.setDead(true);
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
