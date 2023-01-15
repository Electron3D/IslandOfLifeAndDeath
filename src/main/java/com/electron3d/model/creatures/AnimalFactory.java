package com.electron3d.model.creatures;

import com.electron3d.model.creatures.animals.herbivores.*;
import com.electron3d.model.creatures.animals.predators.*;
import com.electron3d.model.island.Field;

public class AnimalFactory {
    public Animal createAnimal(String animalType, Field location) {
        return switch (animalType) {
            case "Boar" -> new Boar(location);
            case "Buffalo" -> new Buffalo(location);
            case "Caterpillar" -> new Caterpillar(location);
            case "Deer" -> new Deer(location);
            case "Duck" -> new Duck(location);
            case "Goat" -> new Goat(location);
            case "Horse" -> new Horse(location);
            case "Mouse" -> new Mouse(location);
            case "Rabbit" -> new Rabbit(location);
            case "Sheep" -> new Sheep(location);
            case "Bear" -> new Bear(location);
            case "Eagle" -> new Eagle(location);
            case "Fox" -> new Fox(location);
            case "Python" -> new Python(location);
            case "Wolf" -> new Wolf(location);
            default -> throw new RuntimeException();
        };
    }
}
