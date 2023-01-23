package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.island.Cell;

public abstract class HerbivoresAnimal extends Animal implements Herbivores {
    public HerbivoresAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double eat(Eatable food) {
        //eatPlant((Plant) food);
        return 0;
    }
}
