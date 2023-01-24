package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalType;

import java.util.*;

public class EatingChancesConfig {
    private static EatingChancesConfig INSTANCE;

    private final Map<AnimalType, Map<String, Double>> eatingChancesForAllAnimals = new HashMap<>();

    public static EatingChancesConfig getInstance() {
        if (INSTANCE == null) {
            EatingChancesConfigBuilder eatingChancesConfigBuilder =
                    new EatingChancesConfigBuilder(new EatingChancesConfig());
            INSTANCE = eatingChancesConfigBuilder.buildAndGetConfig();
        }
        return INSTANCE;
    }
    private EatingChancesConfig() {

    }
    public Map<AnimalType, Map<String, Double>> getEatingChancesForAllAnimals() {
        return eatingChancesForAllAnimals;
    }
}
