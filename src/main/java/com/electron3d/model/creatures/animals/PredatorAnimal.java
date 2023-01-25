package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.island.Cell;

import java.util.List;
import java.util.Random;

public abstract class PredatorAnimal extends Animal implements Predatory {
    public PredatorAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    protected List<Eatable> getFoodListFromCell() {
        return this.getCurrentLocation().getAnimalsOnCellCopy()
                .stream()
                .filter(x -> x instanceof Eatable)
                .map(x -> (Eatable) x).toList();
    }

    /**
     * Filtering animals from all eatable on island cell,
     * check is it still alive,
     * filter the same type and chose somebody who the animal has chances to eat
     * @param foodList - all eatable on island
     * @return - filtered animal that was chosen to try to eat it
     */
    @Override
    public Eatable findFood(List<Eatable> foodList) {
        return foodList.stream()
                .filter(x -> x instanceof Animal)
                .filter(y -> !((Animal) y).isDead())
                .filter(z -> ((Animal) z).getProperties().getType() != this.getProperties().getType())
                .filter(t -> this.getProperties().getChancesToEat(((Animal) t).getProperties().getType().getType()) != 0)
                .findFirst()
                .orElse(null);
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
