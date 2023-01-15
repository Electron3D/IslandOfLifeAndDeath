package com.electron3d.model.island;

import com.electron3d.model.*;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalFactory;
import com.electron3d.model.creatures.Plant;

import java.util.*;

public class Island {
    private final int parallelLength;
    private final int meridianLength;
    private final Field[][] fields;
    private final Set<String> animalTypes;
    private final List<Plant> plantsPull  = new ArrayList<>();
    private final List<Animal> animalsPull = new ArrayList<>();

    public Island(int parallelLength, int meridianLength, Set<String> animalTypes) {
        this.parallelLength = parallelLength;
        this.meridianLength = meridianLength;
        this.fields = new Field[meridianLength][parallelLength];
        this.animalTypes = animalTypes;
    }

    public void initFields() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                fields[y][x] = new Field(x, y);
                Field field = fields[y][x];
                field.plantsOnTheField.addAll(initPlants(field));
                field.animalsOnTheField.addAll(initAnimals(field));
            }
        }
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                field.possibleWays.addAll(initPossibleWays(field));
            }
        }
    }

    private List<Plant> initPlants(Field field) {
        Random startingPlantsCountChooser = new Random();
        field.setAmountOfPlantsOnTheField(startingPlantsCountChooser.nextInt(Plant.BOUND_ON_THE_SAME_FIELD));
        List<Plant> plantsOnTheField = new ArrayList<>();
        for (int i = 0; i < field.getAmountOfPlantsOnTheField(); i++) {
            plantsOnTheField.add(new Plant(field));
        }
        plantsPull.addAll(plantsOnTheField);
        return plantsOnTheField;
    }

    private List<Animal> initAnimals(Field field) {
        Random startingAnimalsCountChooser = new Random();
        IslandSimulationConfig config = IslandSimulationConfig.getInstance();
        for (String animalType : animalTypes) {
            field.amountOfAnimalsOnTheField.put(animalType, startingAnimalsCountChooser.nextInt(config.getBoundForType(animalType)));
        }
        AnimalFactory factory = new AnimalFactory();
        List<Animal> animalsOnTheField = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : field.amountOfAnimalsOnTheField.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                Animal animal = factory.createAnimal(entry.getKey(), field);
                animalsOnTheField.add(animal);
            }
        }
        animalsPull.addAll(animalsOnTheField);
        return animalsOnTheField;
    }
    private List<Field> initPossibleWays(Field field) {
        List<Field> possibleWays = new ArrayList<>();
        int i0 = field.getX();
        int j0 = field.getY();
        int height = fields.length;
        int width = fields[0].length;
        for (int i = i0 - 1; i <= i0 + 1; ++i) {
            for (int j = j0 - 1; j <= j0 + 1; ++j) {
                if (0 <= i && i < height && 0 <= j && j < width && (i != i0 || j != j0)) {
                    possibleWays.add(fields[i][j]);
                }
            }
        }
        return possibleWays;
    }

    public void growPlants() {
        List<Plant> newGrownPlants = new ArrayList<>();
        for (Plant plant : plantsPull) {
            if (plant.grow()) {
                newGrownPlants.add(new Plant(plant.getLocation()));
            }
        }
        plantsPull.addAll(newGrownPlants);
    }

    public void liveADay() {
        for (Animal animal : animalsPull) {
            animal.doAnimalStuff();
        }
    }

    public List<Plant> getPlantsPull() {
        return plantsPull;
    }

    public List<Animal> getAnimalsPull() {
        return animalsPull;
    }

    @Override
    public String toString() {
        StringBuilder fieldsToString = new StringBuilder();
        for (Field[] fieldsOnTheSameMeridian : fields) {
            for (Field field : fieldsOnTheSameMeridian) {
                fieldsToString.append(field.toString()).append(" ");
                if (field.getX() < 10) {
                    fieldsToString.append(" ");
                }
                if (field.getY() < 10) {
                    fieldsToString.append(" ");
                }
                if (field.getAmountOfPlantsOnTheField() < 100) {
                    fieldsToString.append(" ");
                }
            }
            fieldsToString.append("\n");
        }
        return "Island\n{" +
                "parallelLength=" + parallelLength +
                ", meridianLength=" + meridianLength +
                ", fields=\n" + fieldsToString +
                "}";
    }
}



