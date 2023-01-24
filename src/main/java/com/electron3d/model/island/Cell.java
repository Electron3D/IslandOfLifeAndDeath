package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalFactory;
import com.electron3d.model.creatures.Plant;
import com.electron3d.model.creatures.PlantProperties;

import java.util.*;
import java.util.stream.Collectors;

public class Cell {

    //todo private modifiers and correct access for collections
    public final Set<Animal> animalsOnTheCell = new HashSet<>();
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
            List<Plant> newGrownPlants = new ArrayList<>();
            Plant plant = plantsOnTheCell.get(i);
            int numberOfNewGrownPlants = plant.grow();
            for (int j = 0; j < numberOfNewGrownPlants; j++) {
                newGrownPlants.add(new Plant(properties, this));
            }
            addPlants(newGrownPlants);
        }
    }

    public void doAnimalStuff() {
        List<Animal> diedAnimalsToday = new ArrayList<>();
        List<Animal> newBornAnimalsToday = new ArrayList<>();
        //todo redo with iterator?? concurrent modification exception
        for (Animal animal : animalsOnTheCell) {
            boolean breedSucceed = animal.liveADay();
            if (animal.isDead()) {
                diedAnimalsToday.add(animal);
            }
            if (breedSucceed) {
                AnimalFactory factory = new AnimalFactory();
                Animal animalToAdd = factory.createAnimal(animal.getProperties().getType(), animal.getCurrentLocation());
                newBornAnimalsToday.add(animalToAdd);
            }
        }
        buryAnimals(diedAnimalsToday);
        releaseNewBornAnimals(newBornAnimalsToday);
    }

    public void decomposeTheCorpses() {
        List<Animal> deadAnimals = animalsOnTheCell.stream().filter(Animal::isDead).toList();
        buryAnimals(deadAnimals);
    }

    private void releaseNewBornAnimals(List<Animal> newBornAnimalsToday) {
        for (Animal newBornAnimal : newBornAnimalsToday) {
            addAnimal(newBornAnimal);
        }
        newBornAnimalsCounter = newBornAnimalsCounter + newBornAnimalsToday.size();
    }

    private void buryAnimals(List<Animal> diedAnimalsToday) {
        graveYard.addAll(diedAnimalsToday);
        for (Animal deadAnimal : diedAnimalsToday) {
            deleteAnimal(deadAnimal);
        }
    }

    private void addPlants(List<Plant> newGrownPlantsToAdd) {
        synchronized (plantsOnTheCell) {
            plantsOnTheCell.addAll(newGrownPlantsToAdd);
        }
    }

    public void deletePlant(Plant plantToDelete) {
        synchronized (plantsOnTheCell) {
            plantsOnTheCell.remove(plantToDelete);
        }
    }

    public void addAnimal(Animal animalToAdd) {
        synchronized (animalsOnTheCell) {
            animalsOnTheCell.add(animalToAdd);
        }
    }

    public void deleteAnimal(Animal animalToDelete) {
        synchronized (animalsOnTheCell) {
            animalsOnTheCell.remove(animalToDelete);
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
