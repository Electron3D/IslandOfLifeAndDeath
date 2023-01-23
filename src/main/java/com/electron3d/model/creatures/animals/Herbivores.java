package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Plant;

public interface Herbivores {
    default boolean eatPlant(Plant food) {
        return false;
    }

}
