package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AnimalsConfig extends Config {
    private static AnimalsConfig INSTANCE;
    private final List<String> animalsTypes = new ArrayList<>();
    private final List<AnimalProperties> animalsProperties = new ArrayList<>();

    public static AnimalsConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnimalsConfigBuilder<>(new AnimalsConfig()).buildAndGetConfig();
        }
        return INSTANCE;
    }

    private AnimalsConfig() {
    }

    public List<String> getAnimalsTypes() {
        return animalsTypes;
    }

    public List<AnimalProperties> getAnimalsProperties() {
        return animalsProperties;
    }

    private static class AnimalsConfigBuilder<T extends AnimalsConfig> extends ConfigBuilder<T> {
        private static final String ANIMALS_SPECS_FILE_NAME = "animalsSpecs.csv";

        public AnimalsConfigBuilder(T animalsConfig) {
            this.toReturn = animalsConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
            List<String> lines = readLinesFromCsv();
            toReturn.getAnimalsTypes().addAll(getAnimalsNamesFrom(lines));
            toReturn.getAnimalsProperties().addAll(getAllAnimalPropertiesFrom(lines));
        }

        @Override
        protected List<String> readLinesFromCsv() {
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(this.getClass().getResource("/" + ANIMALS_SPECS_FILE_NAME)).getPath()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return lines;
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
