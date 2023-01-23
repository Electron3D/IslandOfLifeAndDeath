package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.creatures.animals.herbivores.Caterpillar;
import com.electron3d.model.island.Cell;

import java.util.List;

public abstract class HerbivoresAndCaterpillarEatingAnimal extends Animal implements Herbivores, Predatory {
    public HerbivoresAndCaterpillarEatingAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double eat(List<Eatable> food) {
        Plant dish = (Plant) food.parallelStream().filter(x -> x instanceof Plant).findFirst().orElseThrow();
        double satisfactionLevel = eatPlant(dish);
        boolean stillHungry = satisfactionLevel > amountOfFoodEnoughToBeSatisfied;
        if (stillHungry) {
            Caterpillar dessert = (Caterpillar) food.parallelStream().filter(x -> x instanceof Caterpillar).findFirst().orElseThrow();
            satisfactionLevel = satisfactionLevel + eatCaterpillar(dessert);
        }
        return satisfactionLevel;
    }

    private double eatCaterpillar(Caterpillar food) {
        //todo
        return 1;
    }
}
