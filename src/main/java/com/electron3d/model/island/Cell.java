package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.*;

import java.util.*;
import java.util.concurrent.*;

public class Cell {
    private final Map<AnimalType, List<Animal>> animalsOnCellByType = new HashMap<>();
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
        List<Animal> animalsOnCell = getAnimalsOnCell();
        try {
            executorService.invokeAll(animalsOnCell);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Animal animal : animalsOnCell) {
            if (animal.isBredSuccessfullyToday()) {
                AnimalType type = animal.getProperties().getType();
                int boundOfThisTypeAnimalOnCell = animal.getProperties().getBoundOnTheSameField();
                long amountOfAnimalsThisTypeOnCell = animalsOnCellByType.get(type).size();
                if (amountOfAnimalsThisTypeOnCell + 1 < boundOfThisTypeAnimalOnCell) {
                    AnimalFactory factory = new AnimalFactory();
                    Animal animalToAdd = factory.createAnimal(type, animal.getCurrentLocation());
                    releaseNewBornAnimal(animalToAdd);
                }
            }
        }
        executorService.shutdown();
    }

    public void decomposeTheCorpses() {
        animalsOnCellByType.values().forEach(animalsOnCell -> animalsOnCell
                .stream()
                .filter(Animal::isDead)
                .forEach(this::buryAnimal));
    }

    public void setNewDay() {
        getAnimalsOnCell().forEach(animal -> {
            animal.setWalkedTodayFalse();
            animal.setBredSuccessfullyTodayFalse();
        });
    }

    private void releaseNewBornAnimal(Animal newBornAnimalToday) {
        addAnimal(newBornAnimalToday);
        newBornAnimalsCounter++;
    }

    public void buryAnimal(Animal deadAnimal) {
        graveYard.add(deadAnimal);
        deleteAnimal(deadAnimal);
    }

    public void addPlant(Plant plantToAdd) {
        plantsOnCell.add(plantToAdd);
    }

    public void deletePlant(Plant plantToDelete) {
        plantsOnCell.remove(plantToDelete);
    }

    public void addAnimal(Animal animalToAdd) {
        AnimalType type = animalToAdd.getProperties().getType();
        animalsOnCellByType.putIfAbsent(type, new CopyOnWriteArrayList<>());
        animalsOnCellByType.get(type).add(animalToAdd);
    }
    public void deleteAnimal(Animal animalToDelete) {
        AnimalType type = animalToDelete.getProperties().getType();
        animalsOnCellByType.get(type).remove(animalToDelete);
    }

    public void addPossibleWays(List<Cell> possibleWaysToAdd) {
        possibleWays.addAll(possibleWaysToAdd);
    }

    public List<Animal> getAnimalsOnCell() {
        return animalsOnCellByType.values().stream().flatMap(Collection::stream).toList();
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
        return animalsOnCellByType.values().stream().mapToInt(List::size).sum();
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|pl:" + getAmountOfPlantsOnCell() + "|an:" + getAmountOfAnimalsOnCell() + "}";
    }
}
