package com.electron3d.model.creatures.animals.herbivores;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.OmnivorousAnimal;
import com.electron3d.model.island.Cell;

public class Duck extends OmnivorousAnimal implements Eatable  {
    public Duck(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double restoreHP() {
        return getProperties().getWeight();
    }
}
