package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalFactory;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.creatures.PlantProperties;

import java.util.*;

public class Field {

    //todo private modifiers and correct access for collections
    public final Map<String, Integer> amountOfAnimalsOnTheField = new HashMap<>();
    public final List<Animal> animalsOnTheField = new ArrayList<>();
    public final List<Plant> plantsOnTheField = new ArrayList<>();
    public final List<Field> possibleWays = new ArrayList<>();
    private final List<Animal> graveYard = new ArrayList<>();
    private int newBornAnimalsCounter;
    private final int x;
    private final int y;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void growPlants() {
        PlantProperties properties = AnimalsConfig.getInstance().getPlantProperties();
        for (int i = 0; i < plantsOnTheField.size(); i++) {
            Plant plant = plantsOnTheField.get(i);
            List<Plant> newGrownPlants = new ArrayList<>();
            int numberOfNewGrownPlants = plant.grow();
            Field location = plant.getLocation();
            for (int j = 0; j < numberOfNewGrownPlants; j++) {
                newGrownPlants.add(new Plant(properties, location));
            }
            addPlants(newGrownPlants, location);
        }
    }

    public void doAnimalStuff() {
        List<Animal> diedAnimalsToday = new ArrayList<>();
        List<Animal> newBornAnimalsToday = new ArrayList<>();
        for (Animal animal : animalsOnTheField) {
            Map<String, Boolean> resultsOfTheDay = animal.liveADay();
            boolean diedThisDay = resultsOfTheDay.get("diedThisDay");
            boolean breedSucceed = resultsOfTheDay.get("breedSucceed");
            if (diedThisDay) {
                diedAnimalsToday.add(animal);
            }
            if (breedSucceed) {
                AnimalFactory factory = new AnimalFactory();
                Animal animalToAdd = factory.createAnimal(animal.getProperties().getType(), animal.getLocation());
                newBornAnimalsToday.add(animalToAdd);
            }
        }
        buryAnimals(diedAnimalsToday);
        releaseNewBornAnimals(newBornAnimalsToday);
    }
    private void releaseNewBornAnimals(List<Animal> newBornAnimalsToday) {
        for (Animal newBornAnimal : newBornAnimalsToday) {
            addAnimal(newBornAnimal, newBornAnimal.getLocation());
        }
        newBornAnimalsCounter = newBornAnimalsCounter + newBornAnimalsToday.size();
    }

    private void buryAnimals(List<Animal> diedAnimalsToday) {
        for (Animal deadAnimal : diedAnimalsToday) {
            deleteAnimal(deadAnimal, deadAnimal.getLocation());
        }
        graveYard.addAll(diedAnimalsToday);
    }

    public void addPlants(List<Plant> newGrownPlantsToAdd, Field location) {
        synchronized (location.plantsOnTheField) {
            location.plantsOnTheField.addAll(newGrownPlantsToAdd);
        }
    }

    public synchronized boolean deletePlant(Plant plantToDelete) {
        return plantsOnTheField.remove(plantToDelete);
    }

    public void addAnimal(Animal animalToAdd, Field location) {
        String animalTypeToAdd = animalToAdd.getProperties().getType();
        synchronized (location.animalsOnTheField) {
            location.animalsOnTheField.add(animalToAdd);
        }
        synchronized (location.amountOfAnimalsOnTheField) {
            location.amountOfAnimalsOnTheField.put(animalTypeToAdd, location.amountOfAnimalsOnTheField.get(animalTypeToAdd) + 1);
        }
    }

    public void deleteAnimal(Animal animalToDelete, Field location) {
        synchronized (location.animalsOnTheField) {
            location.animalsOnTheField.remove(animalToDelete);
        }
        synchronized (location.amountOfAnimalsOnTheField) {
            String animalType = animalToDelete.getProperties().getType();
            location.amountOfAnimalsOnTheField.put(animalType, location.amountOfAnimalsOnTheField.get(animalType) - 1);
        }
    }

    public Animal getTheOldestAnimal() {
        return animalsOnTheField.stream().max(Comparator.comparingInt(Animal::getDaysAliveCounter)).orElseThrow();
    }

    public int getGraveYardSize() {
        return graveYard.size();
    }

    public int getNewBornAnimalsCounter() {
        return newBornAnimalsCounter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAmountOfPlantsOnTheField() {
        return plantsOnTheField.size();
    }
    public int getAmountOfAnimalsOnTheField() {
        return amountOfAnimalsOnTheField.values().stream().reduce(Integer::sum).orElseThrow();
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|plants:" + getAmountOfPlantsOnTheField() + "|animals:" + getAmountOfAnimalsOnTheField() + "}"; //todo
    }
}
