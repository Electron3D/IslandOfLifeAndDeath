package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.*;

import java.util.*;

public class Island {
    private final int xDimension;
    private final int yDimension;
    private final Field[][] fields;
    private final List<String> animalTypes;

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
                initPlants(field);
                initAnimals(field);
            }
        }
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                field.possibleWays.addAll(initPossibleWays(field));
            }
        }
    }

    private void initPlants(Field field) {
        Random startingPlantsCountChooser = new Random();
        PlantProperties properties = AnimalsConfig.getInstance().getPlantProperties();
        int amountOfPlantsOnTheField = startingPlantsCountChooser.nextInt(properties.getBoundOnTheSameField() + 1);
        for (int i = 0; i < amountOfPlantsOnTheField; i++) {
            field.plantsOnTheField.add(new Plant(properties, field));
        }
    }

    private void initAnimals(Field field) {
        Random startingAnimalsCountChooser = new Random();
        List<AnimalProperties> animalsProperties = AnimalsConfig.getInstance().getAnimalsProperties();
        AnimalFactory factory = new AnimalFactory();
        for (String animalType : animalTypes) {
            AnimalProperties animalProperties = animalsProperties.stream().filter(x -> animalType.equals(x.getType())).findFirst().orElseThrow();
            int startingAnimalsCount = startingAnimalsCountChooser.nextInt(animalProperties.getBoundOnTheSameField() + 1);
            for (int i = 0; i < startingAnimalsCount; i++) {
                Animal animal = factory.createAnimal(animalType, field);
                animal.setAdult(true);
                field.animalsOnTheField.add(animal);
            }
            field.amountOfAnimalsOnTheField.put(animalType, field.animalsOnTheField.size());
        }
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

    public void live() {
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                field.growPlants();
                field.doAnimalStuff();
            }
        }
    }

    public void printStats() {
        int totalNumberOfPlants = 0;
        int totalNumberOfAnimals = 0;
        int totalNumberOfDeadAnimals = 0;
        int totalNumberOfNewBornAnimals = 0;
        Animal theOldestAnimal = getTheOldestAnimal();
        for (int y = 0; y < fields.length; y++) {
            for (int x = 0; x < fields[y].length; x++) {
                Field field = fields[y][x];
                totalNumberOfAnimals = totalNumberOfAnimals + field.amountOfAnimalsOnTheField.values().stream().reduce(Integer::sum).orElseThrow();
                totalNumberOfPlants = totalNumberOfPlants + field.getAmountOfPlantsOnTheField();
                totalNumberOfDeadAnimals = totalNumberOfDeadAnimals + field.getGraveYardSize();
                totalNumberOfNewBornAnimals = totalNumberOfNewBornAnimals + field.getNewBornAnimalsCounter();
            }
        }
        System.out.println("Plants total: " + totalNumberOfPlants);
        System.out.println("Animals total: " + totalNumberOfAnimals);
        System.out.println("Animals died: " + totalNumberOfDeadAnimals);
        System.out.println("Were born " + totalNumberOfNewBornAnimals + " animals in total.");
        System.out.println("The oldest animal is: " + theOldestAnimal + " lives already " + theOldestAnimal.getDaysAliveCounter() + " days.");
    }

    private Animal getTheOldestAnimal() {
        Animal animal = fields[0][0].getTheOldestAnimal();
        int daysAlive = animal.getDaysAliveCounter();
        for (int y = 0; y < fields.length; y++) {
            for (int x = 1; x < fields[y].length; x++) {
                Field field = fields[y][x];
                Animal animalToCompare = field.getTheOldestAnimal();
                if (daysAlive < animalToCompare.getDaysAliveCounter()) {
                    animal = animalToCompare;
                }
            }
        }
        return animal;
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
                if (field.getAmountOfPlantsOnTheField() < 10) {
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



