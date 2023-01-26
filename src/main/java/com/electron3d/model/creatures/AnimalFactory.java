package com.electron3d.model.creatures;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.animals.herbivores.*;
import com.electron3d.model.creatures.animals.predators.*;
import com.electron3d.model.island.Cell;

public class AnimalFactory {
    public Animal createAnimal(AnimalType animalType, Cell location) {
        AnimalsConfig config = AnimalsConfig.getInstance();
        return switch (animalType) {
            case BOAR -> new Boar(config.getAnimalSpecificationForType(animalType), location);
            case BUFFALO -> new Buffalo(config.getAnimalSpecificationForType(animalType), location);
            case CATERPILLAR -> new Caterpillar(config.getAnimalSpecificationForType(animalType), location);
            case DEER -> new Deer(config.getAnimalSpecificationForType(animalType), location);
            case DUCK -> new Duck(config.getAnimalSpecificationForType(animalType), location);
            case GOAT -> new Goat(config.getAnimalSpecificationForType(animalType), location);
            case HORSE -> new Horse(config.getAnimalSpecificationForType(animalType), location);
            case MOUSE -> new Mouse(config.getAnimalSpecificationForType(animalType), location);
            case RABBIT -> new Rabbit(config.getAnimalSpecificationForType(animalType), location);
            case SHEEP -> new Sheep(config.getAnimalSpecificationForType(animalType), location);
            case BEAR -> new Bear(config.getAnimalSpecificationForType(animalType), location);
            case EAGLE -> new Eagle(config.getAnimalSpecificationForType(animalType), location);
            case FOX -> new Fox(config.getAnimalSpecificationForType(animalType), location);
            case PYTHON -> new Python(config.getAnimalSpecificationForType(animalType), location);
            case WOLF -> new Wolf(config.getAnimalSpecificationForType(animalType), location);
        };
    }
}