package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.AnimalSpecification;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.island.Cell;

public class Deer extends HerbivoresAnimal implements Eatable {
    public Deer(AnimalSpecification properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return getSpecification().getWeight();
    }
}
