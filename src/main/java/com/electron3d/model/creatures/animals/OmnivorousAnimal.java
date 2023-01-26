package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.*;
import com.electron3d.model.island.Cell;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class OmnivorousAnimal extends HerbivoresAnimal implements Predatory {
    public OmnivorousAnimal(AnimalSpecification properties, Cell location) {
        super(properties, location);
    }

    @Override
    protected List<Eatable> getFoodListFromCell() {
            return Stream.concat(
                this.getCurrentLocation().getAnimalsOnCell()
                        .stream()
                        .map(x -> (Eatable) x),
                this.getCurrentLocation().getPlantsOnCell()
                        .stream()
                        .map(x -> (Eatable) x)
        ).toList();
    }

    @Override
    public Eatable findFood(List<Eatable> foodList) {
        return foodList.stream()
                .filter(x -> x instanceof Plant)
                .findFirst()
                .orElse(findAnimal(foodList));
    }

    /**
     * Filtering animals from all eatable on island cell,
     * check is it still alive,
     * filter the same type and chose somebody who the animal has chances to eat
     * @param foodList - all eatable on island
     * @return - filtered animal that was chosen to try to eat it
     */
    public Eatable findAnimal(List<Eatable> foodList) {
        return foodList.stream()
                .filter(x -> x instanceof Animal
                        && !((Animal) x).isDead()
                        && ((Animal) x).getProperties().getType() != this.getProperties().getType()
                        && this.getProperties().getChancesToEat(((Animal) x).getProperties().getType().getType()) != 0)
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
    public double hunt(Animal food) {
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
