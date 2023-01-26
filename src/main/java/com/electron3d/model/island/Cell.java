package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.*;

import java.util.*;
import java.util.concurrent.*;

public class Cell {
    private final List<Animal> animalsOnCell = new CopyOnWriteArrayList<>();
    private final List<Plant> plantsOnCell = new CopyOnWriteArrayList<>();
    private final List<Cell> possibleWays = new CopyOnWriteArrayList<>();
    private final List<Animal> graveYard = new ArrayList<>();
    private int newBornAnimalsCounter;
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void growPlants() {
        PlantSpecification properties = AnimalsConfig.getInstance().getPlantSpecification();
        for (Plant value : plantsOnCell) {
            List<Plant> newGrownPlants = new ArrayList<>();
            int numberOfNewGrownPlants = value.grow();
            for (int j = 0; j < numberOfNewGrownPlants; j++) {
                newGrownPlants.add(new Plant(properties, this));
            }
            plantsOnCell.addAll(newGrownPlants);
        }
    }

    public void doAnimalStuffParallel() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            executorService.invokeAll(animalsOnCell);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Animal animal : animalsOnCell) {
            if (animal.isDead()) {
                buryAnimal(animal);
            }
            if (animal.isBredSuccessfullyToday()) {
                AnimalFactory factory = new AnimalFactory();
                Animal animalToAdd = factory.createAnimal(animal.getProperties().getType(), animal.getCurrentLocation());
                long amountOfAnimalsThisTypeOnCell = animalsOnCell
                        .stream()
                        .filter(a -> a.getProperties().getType().equals(animalToAdd.getProperties().getType()))
                        .count();
                int boundOfThisTypeAnimalOnCell = animalToAdd.getProperties().getBoundOnTheSameField();
                if (amountOfAnimalsThisTypeOnCell + 1 < boundOfThisTypeAnimalOnCell) {
                    releaseNewBornAnimal(animalToAdd);
                }
            }
        }
        executorService.shutdown();
    }

    public void decomposeTheCorpses() {
        animalsOnCell
                .stream()
                .filter(Animal::isDead)
                .forEach(animalsOnCell::remove);
    }

    public void setNewDay() {
        animalsOnCell.forEach(animal -> {
            animal.setWalkedTodayFalse();
            animal.setBredSuccessfullyTodayFalse();
        });
    }

    private void releaseNewBornAnimal(Animal newBornAnimalToday) {
        animalsOnCell.add(newBornAnimalToday);
        newBornAnimalsCounter++;
    }

    public void buryAnimal(Animal deadAnimal) {
        graveYard.add(deadAnimal);
        animalsOnCell.remove(deadAnimal);
    }

    public void addPlant(Plant plantToAdd) {
        plantsOnCell.add(plantToAdd);
    }

    public void deletePlant(Plant plantToDelete) {
        plantsOnCell.remove(plantToDelete);
    }

    public void addAnimal(Animal animalToAdd) {
        animalsOnCell.add(animalToAdd);
    }

    public void deleteAnimal(Animal animalToDelete) {
        animalsOnCell.remove(animalToDelete);
    }

    public void addPossibleWays(List<Cell> possibleWaysToAdd) {
        possibleWays.addAll(possibleWaysToAdd);
    }

    public Animal getTheOldestAnimal() {
        return animalsOnCell
                .parallelStream()
                .max(Comparator.comparingInt(Animal::getDaysAliveCounter))
                .orElse(null);
    }

    public List<Animal> getAnimalsOnCell() {
        return animalsOnCell;
    }

    public List<Plant> getPlantsOnCell() {
        return plantsOnCell;
    }

    public List<Cell> getPossibleWays() {
        return possibleWays;
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

    public int getAmountOfPlantsOnCell() {
        return plantsOnCell.size();
    }

    public int getAmountOfAnimalsOnCell() {
        return animalsOnCell.size();
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|pl:" + getAmountOfPlantsOnCell() + "|an:" + getAmountOfAnimalsOnCell() + "}";
    }
}
