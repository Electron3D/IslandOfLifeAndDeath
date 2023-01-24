package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.creatures.PlantProperties;

import java.util.*;

public class AnimalsConfig {
    private static AnimalsConfig INSTANCE;
    private final List<AnimalProperties> animalsProperties = new ArrayList<>();
    private PlantProperties plantProperties;

    public static AnimalsConfig getInstance() {
        if (INSTANCE == null) {
            AnimalsConfigBuilder animalsConfigBuilder = new AnimalsConfigBuilder(new AnimalsConfig());
            INSTANCE = animalsConfigBuilder.buildAndGetConfig();
        }
        return INSTANCE;
    }

    private AnimalsConfig() {
    }

    public List<AnimalProperties> getAnimalsProperties() {
        return animalsProperties;
    }

    public AnimalProperties getAnimalPropertiesForType(AnimalType animalType, List<AnimalProperties> allProperties) {
        return allProperties
                .stream()
                .filter(x -> animalType.equals(x.getType()))
                .findFirst()
                .orElseThrow();
    }

    public PlantProperties getPlantProperties() {
        return plantProperties;
    }

    public void setPlantProperties(PlantProperties plantProperties) {
        this.plantProperties = plantProperties;
    }
}
