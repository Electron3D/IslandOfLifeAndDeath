package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.island.Field;

public abstract class PredatorAnimal extends Animal implements Predatory {
    public PredatorAnimal(Field location) {
        super(location);
    }
}
