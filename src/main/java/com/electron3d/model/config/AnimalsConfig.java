package com.electron3d.model.config;

import com.electron3d.model.creatures.AnimalProperties;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.creatures.PlantProperties;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalsConfig {
    private static AnimalsConfig INSTANCE;
    private static final String ANIMALS_SPECS_FILE_NAME = "animalsSpecs.csv";
    private final List<AnimalProperties> animalsProperties = new ArrayList<>();
    private PlantProperties plantProperties;

    public static AnimalsConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnimalsConfig();
            INSTANCE.initFieldsFromSourceFile();
        }
        return INSTANCE;
    }

    private AnimalsConfig() {
    }

    public AnimalProperties getAnimalPropertiesForType(AnimalType animalType, List<AnimalProperties> allProperties) {
        return allProperties
                .stream()
                .filter(x -> animalType.equals(x.getType()))
                .findFirst()
                .orElseThrow();
    }

    public List<AnimalProperties> getAnimalsProperties() {
        return animalsProperties;
    }

    public PlantProperties getPlantProperties() {
        return plantProperties;
    }

    protected void initFieldsFromSourceFile() {
        List<String> lines = ConfigReader.readLinesFromCsv(ANIMALS_SPECS_FILE_NAME);
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String type = line.split(",")[0];
            if ("plant".equalsIgnoreCase(type)) {
                setPlantProperties(type, line);
            }
        }
        animalsProperties.addAll(getAllAnimalPropertiesFrom(lines));
    }

    public void setPlantProperties(String type, String line) {
        String[] values = line.split(",");
        double weight = Double.parseDouble(values[1]);
        int boundOnTheSameField = Integer.parseInt(values[2]);
        plantProperties = new PlantProperties(type, weight, boundOnTheSameField);
    }

    private List<AnimalProperties> getAllAnimalPropertiesFrom(List<String> lines) {
        String[] animalPropertiesNames = lines.get(0).split(",");
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
}
