package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.AnimalSpecification;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.OmnivorousAnimal;
import com.electron3d.model.island.Cell;

public class Mouse extends OmnivorousAnimal implements Eatable {
    public Mouse(AnimalSpecification properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return getSpecification().getWeight();
    }
}
