package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;

public interface Predatory {
    default boolean hunt(Animal food) {
        return false;
    }
}
