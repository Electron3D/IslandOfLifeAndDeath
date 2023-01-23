package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.island.Cell;

public abstract class PredatorAnimal extends Animal implements Predatory {
    public PredatorAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double eat(Eatable food) {
        //hunt((Animal) food);
        return 0;
    }
}
