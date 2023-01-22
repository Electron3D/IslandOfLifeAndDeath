package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalFactory;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.creatures.PlantProperties;

import java.util.*;

public class Cell {

    //todo private modifiers and correct access for collections
    public final List<Animal> animalsOnTheCell = new ArrayList<>();
    public final List<Plant> plantsOnTheCell = new ArrayList<>();
    public final List<Cell> possibleWays = new ArrayList<>();
    private final List<Animal> graveYard = new ArrayList<>();
    private int newBornAnimalsCounter;
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void growPlants() {
        PlantProperties properties = AnimalsConfig.getInstance().getPlantProperties();
        for (int i = 0; i < plantsOnTheCell.size(); i++) {
            Plant plant = plantsOnTheCell.get(i);
            List<Plant> newGrownPlants = new ArrayList<>();
            int numberOfNewGrownPlants = plant.grow();
            Cell location = plant.getLocation();
            for (int j = 0; j < numberOfNewGrownPlants; j++) {
                newGrownPlants.add(new Plant(properties, location));
            }
            addPlants(newGrownPlants, location);
        }
    }

    public void doAnimalStuff() {
        List<Animal> diedAnimalsToday = new ArrayList<>();
        List<Animal> newBornAnimalsToday = new ArrayList<>();
        for (Animal animal : animalsOnTheCell) {
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

    private void addPlants(List<Plant> newGrownPlantsToAdd, Cell location) {
        synchronized (location.plantsOnTheCell) {
            location.plantsOnTheCell.addAll(newGrownPlantsToAdd);
        }
    }

    private synchronized boolean deletePlant(Plant plantToDelete) {
        return plantsOnTheCell.remove(plantToDelete);
    }

    private void addAnimal(Animal animalToAdd, Cell location) {
        synchronized (location.animalsOnTheCell) {
            location.animalsOnTheCell.add(animalToAdd);
        }
    }

    private void deleteAnimal(Animal animalToDelete, Cell location) {
        synchronized (location.animalsOnTheCell) {
            location.animalsOnTheCell.remove(animalToDelete);
        }
    }

    public Animal getTheOldestAnimal() {
        return animalsOnTheCell.parallelStream().max(Comparator.comparingInt(Animal::getDaysAliveCounter)).orElseThrow();
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
        return plantsOnTheCell.size();
    }
    public int getAmountOfAnimalsOnTheCell() {
        return animalsOnTheCell.size();
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|plants:" + getAmountOfPlantsOnTheField() + "|animals:" + getAmountOfAnimalsOnTheCell() + "}";
    }
}
