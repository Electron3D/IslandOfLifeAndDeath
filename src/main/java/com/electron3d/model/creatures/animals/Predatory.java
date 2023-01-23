package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;

public interface Predatory {
    default double hunt(Animal food) {
        //todo
        return 1;
    }
}
