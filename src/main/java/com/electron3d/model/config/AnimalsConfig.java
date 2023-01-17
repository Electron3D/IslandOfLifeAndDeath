package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalsConfig extends Config {
    private static AnimalsConfig INSTANCE;
    private final List<String> animalsNames = new ArrayList<>();
    private final List<AnimalProperties> animalsProperties = new ArrayList<>();

    public static AnimalsConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnimalsConfigBuilder<>(new AnimalsConfig()).buildAndGetConfig();
        }
        return INSTANCE;
    }

    private AnimalsConfig() {
    }

    public List<String> getAnimalsNames() {
        return animalsNames;
    }

    public List<AnimalProperties> getAnimalsProperties() {
        return animalsProperties;
    }

    public static class AnimalsConfigBuilder<T extends AnimalsConfig> extends ConfigBuilder<T> {
        private static final String ANIMALS_SPECS_FILE_NAME = "animalsSpecs.csv";

        public AnimalsConfigBuilder(T animalsConfig) {
            this.toReturn = animalsConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
            List<String> lines = readLinesFromCsv();
            toReturn.getAnimalsNames().addAll(getAnimalsNamesFrom(lines));
            toReturn.getAnimalsProperties().addAll(getAllAnimalPropertiesFrom(lines));
        }

        private List<String> readLinesFromCsv() {
            return new ArrayList<>();//todo
        }

        private List<String> getAnimalsNamesFrom(List<String> lines) {
            List<String> animalsNames = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String name = getName(line);
                animalsNames.add(name);
            }
            return animalsNames;
        }

        private List<AnimalProperties> getAllAnimalPropertiesFrom(List<String> lines) {
            String[] animalPropertiesNames = getHeader(lines).split(",");
            return lines.subList(1, lines.size()).stream().map(line -> {
                String type = "";
                double weight = -1;
                int range = -1;
                int boundOnTheSameField = -1;
                double amountOfFoodToBeFull = -1;
                String[] values = line.split(",");
                for (int i = 0; i < animalPropertiesNames.length; i++) {
                    var value = values[i];
                    switch (animalPropertiesNames[i]) {
                        case "type" -> type = value;
                        case "weight" -> weight = Double.parseDouble(value);
                        case "range" -> range = Integer.parseInt(value);
                        case "boundOnTheSameField" -> boundOnTheSameField = Integer.parseInt(value);
                        case "amountOfFoodToBeFull" -> amountOfFoodToBeFull = Double.parseDouble(value);
                    }
                }
                return new AnimalProperties(type, weight, range, boundOnTheSameField, amountOfFoodToBeFull);
            }).collect(Collectors.toList());
        }

        private String getName(String line) {
            return line.split(",")[0];
        }

        private String getHeader(List<String> lines) {
            return lines.get(0);
        }

        @Override
        public T buildAndGetConfig() {
            initFieldsFromSourceFile();
            //todo check validation
            return toReturn;
        }

    }
}