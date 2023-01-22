package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.HerbivoresAndCaterpillarEatingAnimal;
import com.electron3d.model.island.Cell;

public class Mouse extends HerbivoresAndCaterpillarEatingAnimal implements Eatable {
    public Mouse(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return 0;
    }
}
