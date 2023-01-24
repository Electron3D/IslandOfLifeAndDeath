package com.electron3d.model.creatures;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.animals.herbivores.*;
import com.electron3d.model.creatures.animals.predators.*;
import com.electron3d.model.island.Cell;

import java.util.List;

public class AnimalFactory {
    public Animal createAnimal(AnimalType animalType, Cell location) {
        AnimalsConfig config = AnimalsConfig.getInstance();
        List<AnimalProperties> allProperties = config.getAnimalsProperties();
        return switch (animalType) {
            case BOAR -> new Boar(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case BUFFALO -> new Buffalo(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case CATERPILLAR -> new Caterpillar(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case DEER -> new Deer(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case DUCK -> new Duck(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case GOAT -> new Goat(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case HORSE -> new Horse(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case MOUSE -> new Mouse(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case RABBIT -> new Rabbit(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case SHEEP -> new Sheep(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case BEAR -> new Bear(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case EAGLE -> new Eagle(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case FOX -> new Fox(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case PYTHON -> new Python(config.getAnimalPropertiesForType(animalType, allProperties), location);
            case WOLF -> new Wolf(config.getAnimalPropertiesForType(animalType, allProperties), location);
        };
    }
}