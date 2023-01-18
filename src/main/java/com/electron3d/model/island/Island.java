package com.electron3d.model.island;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalFactory;
import com.electron3d.model.creatures.Plant;

import java.util.*;

public class Island {
    private final int xDimension;
    private final int yDimension;
    private final Field[][] fields;
    private final List<String> animalTypes;
    private final List<Plant> plantsPull  = new ArrayList<>();
    private final List<Animal> animalsPull = new ArrayList<>();

    public Island(int xDimension, int yDimension, List<String> animalTypes) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.fields = new Field[yDimension][xDimension];
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
        field.setAmountOfPlantsOnTheField(startingPlantsCountChooser.nextInt(Plant.BOUND_ON_THE_SAME_FIELD)); //todo replace with getPlantsPullSize();
        List<Plant> plantsOnTheField = new ArrayList<>();
        for (int i = 0; i < field.getAmountOfPlantsOnTheField(); i++) {
            plantsOnTheField.add(new Plant(field));
        }
        plantsPull.addAll(plantsOnTheField);
        return plantsOnTheField;
    }

    private List<Animal> initAnimals(Field field) {
        Random startingAnimalsCountChooser = new Random();
        for (String animalType : animalTypes) {
            field.amountOfAnimalsOnTheField.put(animalType, startingAnimalsCountChooser.nextInt(10)); //todo add bound from config
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
        addPlants(newGrownPlants);
    }

    public int getPlantsPullSize() {
        return plantsPull.size();
    }

    public synchronized void addPlants(List<Plant> newGrownPlantsToAdd) {
        plantsPull.addAll(newGrownPlantsToAdd);
    }
    public synchronized boolean deletePlant(Plant plantToDelete) {
        return plantsPull.remove(plantToDelete);
    }

    public void doAnimalStuff() {
        for (Animal animal : animalsPull) {
            animal.liveADay();
        }
    }

    public int getAnimalsPullSize() {
        return animalsPull.size();
    }

    public synchronized void addAnimal(Animal animalToAdd) {
        animalsPull.add(animalToAdd);
    }

    public synchronized boolean deleteAnimal(Animal animalToDelete) {
        return animalsPull.remove(animalToDelete);
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
                "parallelLength=" + xDimension +
                ", meridianLength=" + yDimension +
                ", fields=\n" + fieldsToString +
                "}";
    }
}



