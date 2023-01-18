package com.electron3d.model.creatures;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.animals.herbivores.*;
import com.electron3d.model.creatures.animals.predators.*;
import com.electron3d.model.island.Field;

import java.util.List;

public class AnimalFactory {
    public Animal createAnimal(String animalType, Field location) {
        List<AnimalProperties> allProperties = AnimalsConfig.getInstance().getAnimalsProperties();
        return switch (animalType) {
            case "Boar" -> new Boar(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Buffalo" -> new Buffalo(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Caterpillar" -> new Caterpillar(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Deer" -> new Deer(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Duck" -> new Duck(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Goat" -> new Goat(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Horse" -> new Horse(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Mouse" -> new Mouse(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Rabbit" -> new Rabbit(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Sheep" -> new Sheep(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Bear" -> new Bear(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Eagle" -> new Eagle(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Fox" -> new Fox(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Python" -> new Python(getAnimalPropertiesForType(animalType, allProperties), location);
            case "Wolf" -> new Wolf(getAnimalPropertiesForType(animalType, allProperties), location);
            default -> throw new RuntimeException();
        };
    }

    private AnimalProperties getAnimalPropertiesForType(String animalType, List<AnimalProperties> allProperties) {
        return allProperties
                .stream()
                .filter(x -> animalType.equals(x.getType()))
                .findFirst()
                .orElseThrow();
    }
}