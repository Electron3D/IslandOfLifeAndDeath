package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.creatures.animals.herbivores.Animal;
import com.electron3d.model.island.Cell;

import java.util.List;
import java.util.Random;

public abstract class HerbivoresAndAccidentAnimalEatingAnimal extends HerbivoresAnimal implements Predatory {
    public HerbivoresAndAccidentAnimalEatingAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public Eatable findFood(List<Eatable> foodList) {
        return foodList.parallelStream()
                .filter(x -> x instanceof Plant)
                .findFirst()
                .orElse(findAnimal(foodList));
    }
    public Eatable findAnimal(List<Eatable> foodList) {
        return foodList.parallelStream()
                .filter(x -> x instanceof Animal)
                .filter(y -> !((Animal) y).isDead())
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean eat(Eatable food) {
        double restoredHP;
        if (food instanceof Plant plantToEat) {
            restoredHP = eatPlant(plantToEat);
        } else {
            Animal dessert = (Animal) food;
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
    public double hunt(com.electron3d.model.creatures.Animal food) {
        Random chanceToEat = new Random();
        Eatable exactFood = (Eatable) food;
        AnimalType foodType = food.getProperties().getType();
        double chance = getProperties().getChancesToEat(foodType.getType());
        double restoredHP = 0;
        if (chanceToEat.nextDouble(0, 1) < chance) {
            restoredHP = exactFood.restoreHP();
            food.setDead(true);
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
