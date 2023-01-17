package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.Herbivores;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.island.Field;

public class Deer extends HerbivoresAnimal implements Eatable {
    public Deer(Field location) {
        super(location);
    }

    @Override
    public double restoreHP() {
        return 0;
    }
}
