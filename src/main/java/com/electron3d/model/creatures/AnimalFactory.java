package com.electron3d.model.creatures;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.animals.herbivores.*;
import com.electron3d.model.creatures.animals.predators.*;
import com.electron3d.model.island.Field;

import java.util.List;

public class AnimalFactory {
    public Animal createAnimal(String animalType, Field location) {
        List<AnimalProperties> allProperties = AnimalsConfig.getInstance().getAnimalsProperties();
        return switch (animalType.toLowerCase()) {
            case "boar" -> new Boar(getAnimalPropertiesForType(animalType, allProperties), location);
            case "buffalo" -> new Buffalo(getAnimalPropertiesForType(animalType, allProperties), location);
            case "caterpillar" -> new Caterpillar(getAnimalPropertiesForType(animalType, allProperties), location);
            case "deer" -> new Deer(getAnimalPropertiesForType(animalType, allProperties), location);
            case "duck" -> new Duck(getAnimalPropertiesForType(animalType, allProperties), location);
            case "goat" -> new Goat(getAnimalPropertiesForType(animalType, allProperties), location);
            case "horse" -> new Horse(getAnimalPropertiesForType(animalType, allProperties), location);
            case "mouse" -> new Mouse(getAnimalPropertiesForType(animalType, allProperties), location);
            case "rabbit" -> new Rabbit(getAnimalPropertiesForType(animalType, allProperties), location);
            case "sheep" -> new Sheep(getAnimalPropertiesForType(animalType, allProperties), location);
            case "bear" -> new Bear(getAnimalPropertiesForType(animalType, allProperties), location);
            case "eagle" -> new Eagle(getAnimalPropertiesForType(animalType, allProperties), location);
            case "fox" -> new Fox(getAnimalPropertiesForType(animalType, allProperties), location);
            case "python" -> new Python(getAnimalPropertiesForType(animalType, allProperties), location);
            case "wolf" -> new Wolf(getAnimalPropertiesForType(animalType, allProperties), location);
            default -> throw new RuntimeException("Unknown type of animal");
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