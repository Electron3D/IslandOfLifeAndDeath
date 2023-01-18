package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.island.Field;

public abstract class HerbivoresAnimal extends Animal implements Herbivores {
    public HerbivoresAnimal(AnimalProperties properties, Field location) {
        super(properties, location);
    }
}
