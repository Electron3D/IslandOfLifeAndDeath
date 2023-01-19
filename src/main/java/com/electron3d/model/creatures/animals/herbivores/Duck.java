package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.HerbivoresAndCaterpillarEatingAnimal;
import com.electron3d.model.island.Field;

public class Duck extends HerbivoresAndCaterpillarEatingAnimal implements Eatable  {
    public Duck(AnimalProperties properties, Field location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return 0;
    }
}
