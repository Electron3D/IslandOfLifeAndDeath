package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.island.Field;

public abstract class HerbivoresAndCaterpillarEatingAnimal extends HerbivoresAnimal {
    public HerbivoresAndCaterpillarEatingAnimal(AnimalProperties properties, Field location) {
        super(properties, location);
    }

    public void accidentallyEatCaterpillar() {
    }
}
