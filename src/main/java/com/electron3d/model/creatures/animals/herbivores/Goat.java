package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.island.Cell;

public class Goat extends HerbivoresAnimal implements Eatable {

    public Goat(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return getProperties().getWeight();
    }
}
