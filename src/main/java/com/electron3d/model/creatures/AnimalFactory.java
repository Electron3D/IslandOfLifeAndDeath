package com.electron3d.model.creatures;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.animals.herbivores.*;
import com.electron3d.model.creatures.animals.predators.*;
import com.electron3d.model.island.Cell;

import java.util.List;

public class AnimalFactory {
    public Animal createAnimal(AnimalType animalType, Cell location) {
        List<AnimalProperties> allProperties = AnimalsConfig.getInstance().getAnimalsProperties();
        return switch (animalType) {
            case BOAR -> new Boar(getAnimalPropertiesForType(animalType, allProperties), location);
            case BUFFALO -> new Buffalo(getAnimalPropertiesForType(animalType, allProperties), location);
            case CATERPILLAR -> new Caterpillar(getAnimalPropertiesForType(animalType, allProperties), location);
            case DEER -> new Deer(getAnimalPropertiesForType(animalType, allProperties), location);
            case DUCK -> new Duck(getAnimalPropertiesForType(animalType, allProperties), location);
            case GOAT -> new Goat(getAnimalPropertiesForType(animalType, allProperties), location);
            case HORSE -> new Horse(getAnimalPropertiesForType(animalType, allProperties), location);
            case MOUSE -> new Mouse(getAnimalPropertiesForType(animalType, allProperties), location);
            case RABBIT -> new Rabbit(getAnimalPropertiesForType(animalType, allProperties), location);
            case SHEEP -> new Sheep(getAnimalPropertiesForType(animalType, allProperties), location);
            case BEAR -> new Bear(getAnimalPropertiesForType(animalType, allProperties), location);
            case EAGLE -> new Eagle(getAnimalPropertiesForType(animalType, allProperties), location);
            case FOX -> new Fox(getAnimalPropertiesForType(animalType, allProperties), location);
            case PYTHON -> new Python(getAnimalPropertiesForType(animalType, allProperties), location);
            case WOLF -> new Wolf(getAnimalPropertiesForType(animalType, allProperties), location);
        };
    }

    private AnimalProperties getAnimalPropertiesForType(AnimalType animalType, List<AnimalProperties> allProperties) {
        return allProperties
                .stream()
                .filter(x -> animalType.equals(x.getType()))
                .findFirst()
                .orElseThrow();
    }
}