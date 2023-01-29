package com.electron3d.model.creatures.animals.predators;

import com.electron3d.model.creatures.AnimalSpecification;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.PredatorAnimal;
import com.electron3d.model.island.Cell;

public class Eagle  extends PredatorAnimal implements Eatable {

    public Eagle(AnimalSpecification properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return getSpecification().getWeight();
    }
}
