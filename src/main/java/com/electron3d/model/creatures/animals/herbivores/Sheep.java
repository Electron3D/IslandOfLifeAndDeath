package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.Herbivores;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.island.Field;

public class Sheep extends HerbivoresAnimal implements Eatable {

    public Sheep(AnimalProperties properties, Field location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return 0;
    }
}
