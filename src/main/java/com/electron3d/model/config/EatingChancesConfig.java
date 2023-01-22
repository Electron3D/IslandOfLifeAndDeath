package com.electron3d.model.config;

import java.util.*;

/**
 * Every Config class inheritor should have its own builder as inner class in it extended from ConfigBuilder class.
 */
public class EatingChancesConfig extends Config {
    private static EatingChancesConfig INSTANCE;

    private final Map<String, Map<String, Double>> eatingChancesForAllAnimals = new HashMap<>();

    public static EatingChancesConfig getInstance() {
        if (INSTANCE == null) {
            EatingChancesConfigBuilder<EatingChancesConfig> eatingChancesConfigBuilder =
                    new EatingChancesConfigBuilder<>(new EatingChancesConfig());
            INSTANCE = eatingChancesConfigBuilder.buildAndGetConfig();
        }
        return INSTANCE;
    }
    private EatingChancesConfig() {

    }
    public Map<String, Map<String, Double>> getEatingChancesForAllAnimals() {
        return eatingChancesForAllAnimals;
    }

    /**
     * There is an example of Config "toReturn" that Builder should configure,
     * initialise fields, check validation and then return.
     **/
    private static class EatingChancesConfigBuilder<T extends EatingChancesConfig> extends ConfigBuilder<T> {
        private static final String EAT_CHANCES_FILE_NAME = "chanceToEat.csv";

        public EatingChancesConfigBuilder(T eatingChancesConfig) {
            this.toReturn = eatingChancesConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
            List<String> lines = readLinesFromCsv(EAT_CHANCES_FILE_NAME);
            toReturn.getEatingChancesForAllAnimals().putAll(Objects.requireNonNull(parseLines(lines)));
        }

        private Map<String, Map<String, Double>> parseLines(List<String> lines) {
            Map<String, Map<String, Double>> eatingChancesForAllAnimals = new HashMap<>();
            List<String> foodNames = getFoodNamesFrom(lines);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                List<String> values = parseLine(line);
                String type = values.get(0);
                Map<String, Double> eatChances = new HashMap<>();
                for (int j = 1; j < values.size(); j++) {
                    String foodName = foodNames.get(j);
                    String value = values.get(j);
                    if (!foodName.equals(type)) {
                        eatChances.put(foodName, Double.valueOf(value));
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

        @Override
        public T buildAndGetConfig() {
            initFieldsFromSourceFile();
            return toReturn;
        }
    }
}
