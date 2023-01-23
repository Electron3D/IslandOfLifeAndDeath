package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Plant;

public interface Herbivores {
    default double eatPlant(Plant food) {
        //todo
        return 1;
    }

}
