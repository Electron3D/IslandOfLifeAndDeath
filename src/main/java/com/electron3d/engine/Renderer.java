package com.electron3d.engine;

import com.electron3d.model.IslandSimulation;
import com.electron3d.model.config.IslandSimulationConfig;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.island.Cell;
import com.electron3d.model.island.Island;

import java.util.Arrays;

public class Renderer {
    private final IslandSimulation simulation;
    private final Island island;
    private final Cell[][] cells;

    public Renderer(IslandSimulation simulation) {
        this.simulation = simulation;
        island = simulation.getIsland();
        cells = island.getCells();
    }

    public void printStartSimulationConditions() {
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        int maxTimer = islandSimulationConfig.getMaxTimeOfSimulationInSeconds();
        int timeFlowSpeedMultiplier = islandSimulationConfig.getTimeMultiplier();
        System.out.println("Starting condition:");
        System.out.println("");
    }

    public void printCurrentDay(int timer) {
        System.out.print("Day: " + timer);
    }

    public void snapshotOfTheIsland() {
        System.out.println(island);
        printStats();
    }

    public void printSimulationResults() {
        System.out.println("Time is over! Final results:");
        addSpaces();

    }

    public void printStats() {
        int totalNumberOfPlants = 0;
        int totalNumberOfAnimals = 0;
        int totalNumberOfDeadAnimals = 0;
        int totalNumberOfNewBornAnimals = 0;
        Animal theOldestAnimal = getTheOldestAnimal();
        if (theOldestAnimal == null) {
            System.out.println("All are dead!");
            return;
        }
        String theOldestAnimalName = theOldestAnimal.toString().substring(theOldestAnimal.toString().indexOf(theOldestAnimal.getClass().getSimpleName()));
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Cell cell = cells[y][x];
                totalNumberOfAnimals = totalNumberOfAnimals + cell.getAmountOfAnimalsOnTheCell();
                totalNumberOfPlants = totalNumberOfPlants + cell.getAmountOfPlantsOnTheCell();
                totalNumberOfDeadAnimals = totalNumberOfDeadAnimals + cell.getGraveYardSize();
                totalNumberOfNewBornAnimals = totalNumberOfNewBornAnimals + cell.getNewBornAnimalsCounter();
            }
        }
        System.out.println("Plants total: " + totalNumberOfPlants);
        System.out.println("Animals total: " + totalNumberOfAnimals);
        System.out.println("Animals died: " + totalNumberOfDeadAnimals);
        System.out.println("Were born " + totalNumberOfNewBornAnimals + " animals in total.");
        System.out.println("The oldest animal is: " + theOldestAnimalName + " lives already " + theOldestAnimal.getDaysAliveCounter() + " days.");
    }

    private Animal getTheOldestAnimal() {
        Cell startedCell = Arrays.stream(cells)
                .flatMap(array -> Arrays.stream(array)
                        .filter(cell -> cell.getAmountOfAnimalsOnTheCell() > 0))
                .findFirst()
                .orElse(null);
        if (startedCell == null) {
            return null;
        }
        Animal animal = startedCell.getTheOldestAnimal();
        int daysAlive = animal.getDaysAliveCounter();
        for (int y = 0; y < cells.length; y++) {
            for (int x = 1; x < cells[y].length; x++) {
                Cell cell = cells[y][x];
                Animal animalToCompare = cell.getTheOldestAnimal();
                if (animalToCompare == null) {
                    return animal;
                }
                if (daysAlive < animalToCompare.getDaysAliveCounter()) {
                    animal = animalToCompare;
                }
            }
        }
        return animal;
    }

    private void addSpaces() {
        System.out.println("\n\n\n");
    }
}
