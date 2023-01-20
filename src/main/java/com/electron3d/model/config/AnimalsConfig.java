package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.PlantProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Every Config class inheritor should have its own builder as inner class in it extended from ConfigBuilder class.
 */
public class AnimalsConfig extends Config {
    private static AnimalsConfig INSTANCE;
    private final List<String> animalsTypes = new ArrayList<>();
    private final List<AnimalProperties> animalsProperties = new ArrayList<>();
    private PlantProperties plantProperties;

    public static AnimalsConfig getInstance() {
        if (INSTANCE == null) {
            AnimalsConfigBuilder<AnimalsConfig> animalsConfigBuilder = new AnimalsConfigBuilder<>(new AnimalsConfig());
            INSTANCE = animalsConfigBuilder.buildAndGetConfig();
            INSTANCE.setPlantProperties(animalsConfigBuilder.getPlantProperties());
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

    public PlantProperties getPlantProperties() {
        return plantProperties;
    }

    private void setPlantProperties(PlantProperties plantProperties) {
        this.plantProperties = plantProperties;
    }

    /**
     * There is an example of Config "toReturn" that Builder should configure,
     * initialise fields, check validation and then return.
     **/
    private static class AnimalsConfigBuilder<T extends AnimalsConfig> extends ConfigBuilder<T> {
        private static final String ANIMALS_SPECS_FILE_NAME = "animalsSpecs.csv";
        private PlantProperties plantProperties;

        public AnimalsConfigBuilder(T animalsConfig) {
            this.toReturn = animalsConfig;
        }

        @Override
        protected void initFieldsFromSourceFile() {
            List<String> lines = readLinesFromCsv(ANIMALS_SPECS_FILE_NAME);
            toReturn.getAnimalsTypes().addAll(getAnimalsTypesFrom(lines));
            toReturn.getAnimalsProperties().addAll(getAllAnimalPropertiesFrom(lines));
        }

        private List<String> getAnimalsTypesFrom(List<String> lines) {
            List<String> animalsNames = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String type = getType(line);
                if (isPlant(type)) {
                    setPlantProperties(line);
                } else {
                    animalsNames.add(type);
                }
            }
            return animalsNames;
        }

        private void setPlantProperties(String line) {
            String[] values = line.split(",");
            double weight = Double.parseDouble(values[1]);
            int boundOnTheSameField = Integer.parseInt(values[2]);
            plantProperties = new PlantProperties(weight, boundOnTheSameField);
        }

        private PlantProperties getPlantProperties() {
            return plantProperties;
        }

        private String getType(String line) {
            return line.split(",")[0];
        }

        private boolean isPlant(String type) {
            return "plant".equalsIgnoreCase(type);
        }

        private List<AnimalProperties> getAllAnimalPropertiesFrom(List<String> lines) {
            String[] animalPropertiesNames = getHeader(lines).split(",");
            return lines.subList(1, lines.size()).stream().filter(x -> !(x.startsWith("plant"))).map(line -> {
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
