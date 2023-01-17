package com.electron3d.model.creatures.animals.predators;

import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.PredatorAnimal;
import com.electron3d.model.island.Field;

public class Python  extends PredatorAnimal implements Eatable {

    public Python(Field location) {
        super(location);
    }

    @Override
    public double restoreHP() {
        return 0;
    }
}
