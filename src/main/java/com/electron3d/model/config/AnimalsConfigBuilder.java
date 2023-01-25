package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.creatures.PlantProperties;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalsConfigBuilder {
    private static final String ANIMALS_SPECS_FILE_NAME = "animalsSpecs.csv";
    private final AnimalsConfig toReturn;
    PlantProperties plantProperties;

    public AnimalsConfigBuilder(AnimalsConfig animalsConfig) {
        this.toReturn = animalsConfig;
    }

    protected void initFieldsFromSourceFile() {
        List<String> lines = ConfigReader.readLinesFromCsv(ANIMALS_SPECS_FILE_NAME);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String type = getType(line);
            if (isPlant(type)) {
                setPlantProperties(type, line);
            }
        }
        toReturn.setPlantProperties(getPlantProperties());
        toReturn.getAnimalsProperties().addAll(getAllAnimalPropertiesFrom(lines));
    }

    public void setPlantProperties(String type, String line) {
        String[] values = line.split(",");
        double weight = Double.parseDouble(values[1]);
        int boundOnTheSameField = Integer.parseInt(values[2]);
        plantProperties = new PlantProperties(type, weight, boundOnTheSameField);
    }

    public PlantProperties getPlantProperties() {
        return plantProperties;
    }

    private String getType(String line) {
        return line.split(",")[0];
    }
    private String getCSVHeader(List<String> lines) {
        return lines.get(0);
    }

    private boolean isPlant(String type) {
        return "plant".equalsIgnoreCase(type);
    }

    private List<AnimalProperties> getAllAnimalPropertiesFrom(List<String> lines) {
        String[] animalPropertiesNames = getCSVHeader(lines).split(",");
        Map<AnimalType, Map<String, Double>> eatingChancesForAllAnimals = EatingChancesConfig.getInstance().getEatingChancesForAllAnimals();
        return lines.subList(1, lines.size()).stream().filter(x -> !(x.startsWith("plant"))).map(line -> {
            AnimalType type = null;
            double weight = -1;
            int range = -1;
            int boundOnTheSameCell = -1;
            double amountOfFoodToBeFull = -1;
            Map<String, Double> chancesToEat = new HashMap<>();
            String[] values = line.split(",");
            for (int i = 0; i < animalPropertiesNames.length; i++) {
                var value = values[i];
                switch (animalPropertiesNames[i]) {
                    case "type" -> {
                        type = Arrays.stream(AnimalType.values()).filter(x -> x.getType().equals(value)).findFirst().get();
                        chancesToEat = eatingChancesForAllAnimals.get(type);
                    }
                    case "weight" -> weight = Double.parseDouble(value);
                    case "range" -> range = Integer.parseInt(value);
                    case "boundOnTheSameCell" -> boundOnTheSameCell = Integer.parseInt(value);
                    case "amountOfFoodToBeFull" -> amountOfFoodToBeFull = Double.parseDouble(value);
                }
            }
            return new AnimalProperties(type, weight, range, boundOnTheSameCell, amountOfFoodToBeFull, chancesToEat);
        }).collect(Collectors.toList());
    }

    public AnimalsConfig buildAndGetConfig() {
        initFieldsFromSourceFile();
        //todo check validation
        return toReturn;
    }
}
