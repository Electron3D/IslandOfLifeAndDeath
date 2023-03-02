package com.electron3d.model.island;

import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.creatures.Plant;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Cell {
    private final Map<AnimalType, List<Animal>> animalsOnCellByType = new HashMap<>();
    private final List<Plant> plantsOnCell = new CopyOnWriteArrayList<>();
    private final List<Cell> possibleWays = new CopyOnWriteArrayList<>();
    private final List<Animal> graveYard = new ArrayList<>();
    private int newBornAnimalsCounter;
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException("Variable X can't be negative.");
        }
        if (y < 0) {
            throw new IllegalArgumentException("Variable Y can't be negative.");
        }
        this.x = x;
        this.y = y;
    }

    public void releaseNewBornAnimal(Animal newBornAnimalToday) {
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
        AnimalType type = animalToAdd.getSpecification().getType();
        animalsOnCellByType.putIfAbsent(type, new CopyOnWriteArrayList<>());
        animalsOnCellByType.get(type).add(animalToAdd);
    }
    public void deleteAnimal(Animal animalToDelete) {
        AnimalType type = animalToDelete.getSpecification().getType();
        animalsOnCellByType.get(type).remove(animalToDelete);
    }

    public void addPossibleWays(List<Cell> possibleWaysToAdd) {
        possibleWays.addAll(possibleWaysToAdd);
    }

    public List<Animal> getAnimalsOnCell() {
        return animalsOnCellByType.values().stream().flatMap(Collection::stream).toList();
    }

    public List<Animal> getAnimalsOnCellOfType(AnimalType type) {
        return animalsOnCellByType.get(type);
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
