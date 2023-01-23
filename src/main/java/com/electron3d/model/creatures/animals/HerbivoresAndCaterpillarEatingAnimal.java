package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.island.Cell;

public abstract class HerbivoresAndCaterpillarEatingAnimal extends Animal implements Herbivores, Predatory {
    public HerbivoresAndCaterpillarEatingAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double eat(Eatable food) {
         /*boolean stillHungry = eatPlant((Plant) food); //todo
        if (stillHungry) {
            eatCatarpiller();
        }*/
        return 0;
    }

    private void eatCatarpiller() {
    }
}
