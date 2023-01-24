package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalType;

import java.util.*;

public class EatingChancesConfigBuilder {
    private static final String EAT_CHANCES_FILE_NAME = "chanceToEat.csv";
    private final EatingChancesConfig toReturn;

    public EatingChancesConfigBuilder(EatingChancesConfig eatingChancesConfig) {
        this.toReturn = eatingChancesConfig;
    }

    private void initFieldsFromSourceFile() {
        List<String> lines = ConfigReader.readLinesFromCsv(EAT_CHANCES_FILE_NAME);
        toReturn.getEatingChancesForAllAnimals().putAll(Objects.requireNonNull(parseLines(lines)));
    }

    private Map<AnimalType, Map<String, Double>> parseLines(List<String> lines) {
        Map<AnimalType, Map<String, Double>> eatingChancesForAllAnimals = new HashMap<>();
        List<String> foodNames = getFoodNamesFrom(lines);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            List<String> values = parseLine(line);
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

    private List<String> parseLine(String line) {
        return List.of(line.split(","));
    }

    private List<String> getFoodNamesFrom(List<String> lines) {
        return Arrays.stream(getCSVHeader(lines).split(",")).toList();
    }
    private String getCSVHeader(List<String> lines) {
        return lines.get(0);
    }

    public EatingChancesConfig buildAndGetConfig() {
        initFieldsFromSourceFile();
        return toReturn;
    }
}
