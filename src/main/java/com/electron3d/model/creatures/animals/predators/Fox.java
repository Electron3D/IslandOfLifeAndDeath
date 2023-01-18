package com.electron3d.model.creatures.animals.predators;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.PredatorAnimal;
import com.electron3d.model.island.Field;

public class Fox  extends PredatorAnimal implements Eatable {

    public Fox(AnimalProperties properties, Field location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return 0;
    }
}
