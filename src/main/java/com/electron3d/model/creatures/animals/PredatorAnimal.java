package com.electron3d.model.creatures.animals;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.Eatable;
import com.electron3d.model.creatures.animals.herbivores.Caterpillar;
import com.electron3d.model.island.Cell;

import java.util.List;

public abstract class PredatorAnimal extends Animal implements Predatory {
    public PredatorAnimal(AnimalProperties properties, Cell location) {
        super(properties, location);
    }

    @Override
    public double eat(List<Eatable> food) {
        Animal dish = (Animal) food.parallelStream().filter(x -> x instanceof Animal).findFirst().orElseThrow();
        return hunt(dish);
    }
}
