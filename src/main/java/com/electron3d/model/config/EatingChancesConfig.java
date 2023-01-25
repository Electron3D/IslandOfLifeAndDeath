package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalType;

import java.util.*;

public class EatingChancesConfig {
    private static EatingChancesConfig INSTANCE;
    private static final String EAT_CHANCES_FILE_NAME = "chanceToEat.csv";
    private final Map<AnimalType, Map<String, Double>> eatingChancesForAllAnimals = new HashMap<>();

    public static EatingChancesConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EatingChancesConfig();
            INSTANCE.initFieldsFromSourceFile();
        }
        return INSTANCE;
    }

    private EatingChancesConfig() {

    }

    public Map<AnimalType, Map<String, Double>> getEatingChancesForAllAnimals() {
        return eatingChancesForAllAnimals;
    }

    private void initFieldsFromSourceFile() {
        List<String> lines = ConfigReader.readLinesFromCsv(EAT_CHANCES_FILE_NAME);
        eatingChancesForAllAnimals.putAll(Objects.requireNonNull(parseLines(lines)));
    }

    private Map<AnimalType, Map<String, Double>> parseLines(List<String> lines) {
        Map<AnimalType, Map<String, Double>> eatingChancesForAllAnimals = new HashMap<>();
        List<String> foodNames = Arrays.stream(lines.get(0).split(",")).toList();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            List<String> values = List.of(line.split(","));
            String typeName = values.get(0);
            AnimalType type = Arrays.stream(AnimalType.values()).filter(x -> typeName.equals(x.getType())).findFirst().get();
            Map<String, Double> eatChances = new HashMap<>();
            for (int j = 1; j < values.size(); j++) {
                String foodName = foodNames.get(j);
                String value = values.get(j);
                if (!foodName.equals(type.getType())) {
                    eatChances.put(foodName, Double.parseDouble(value) / 100);
                }
            }
            eatingChancesForAllAnimals.put(type, eatChances);
        }
        return eatingChancesForAllAnimals;
    }
}
