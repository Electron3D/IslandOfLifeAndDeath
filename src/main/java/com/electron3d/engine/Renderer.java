package com.electron3d.engine;

import com.electron3d.model.IslandSimulation;
import com.electron3d.model.config.IslandSimulationConfig;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.island.Cell;
import com.electron3d.model.island.Island;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.electron3d.model.creatures.PlantProperties.ICON;

public class Renderer {
    private final Island island;
    private final Cell[][] cells;

    public Renderer(IslandSimulation simulation) {
        island = simulation.getIsland();
        cells = island.getCells();
    }

    public void printStartSimulationConditions() {
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        int timeFlowSpeedMultiplier = islandSimulationConfig.getTimeMultiplier();
        int maxTimer = islandSimulationConfig.getMaxTimeOfSimulationInSeconds();
        System.out.println("Starting condition:");
        System.out.println("Simulation will continue to " + maxTimer + " day.");
        System.out.println("Time flow speed multiplier set to x" + timeFlowSpeedMultiplier + "\n");
    }

    public void printSimulationStateForDay(int timer) {
        System.out.println("\n\n\n");
        printCurrentDay(timer);
        getSnapshotOfTheIsland();
        printStats();
    }

    public void printCurrentDay(int timer) {
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        int timeFlowSpeedMultiplier = islandSimulationConfig.getTimeMultiplier();
        System.out.println("Day: " + timer + " speed x" + timeFlowSpeedMultiplier);
    }

    public void getSnapshotOfTheIsland() {
        System.out.println(island);
    }

    public void printSimulationResults(int timer) {
        System.out.println("\n\n\n");
        System.out.println("Time is over! Final results:\n");
        System.out.println("The simulation ended on day " + timer);
        getSnapshotOfTheIsland();
        System.out.println();
        printStats();
    }

    public void printStats() {
        int totalNumberOfPlants = 0;
        int totalNumberOfAnimals = 0;
        int totalNumberOfDeadAnimals = 0;
        int totalNumberOfNewBornAnimals = 0;
        for (Cell[] value : cells) {
            for (Cell cell : value) {
                totalNumberOfAnimals = totalNumberOfAnimals + cell.getAmountOfAnimalsOnCell();
                totalNumberOfPlants = totalNumberOfPlants + cell.getAmountOfPlantsOnCell();
                totalNumberOfDeadAnimals = totalNumberOfDeadAnimals + cell.getGraveYardSize();
                totalNumberOfNewBornAnimals = totalNumberOfNewBornAnimals + cell.getNewBornAnimalsCounter();
            }
        }
        Animal theOldestAnimal = getTheOldestAnimal();
        if (theOldestAnimal == null) {
            System.out.println("All are dead!");
        }
        System.out.println("Plants total: " + totalNumberOfPlants + " " + ICON);
        System.out.print("Animals total: " + totalNumberOfAnimals + " ");
        printCountOfEachTypeOfAnimal();
        System.out.println();
        System.out.println("Animals died: " + totalNumberOfDeadAnimals + " \uD83D\uDC80");
        System.out.println("Were born " + totalNumberOfNewBornAnimals + " animals in total \uD83D\uDC76\uD83C\uDFFB");
        if (theOldestAnimal != null) {
            String theOldestAnimalName = theOldestAnimal.toString().substring(theOldestAnimal.toString().indexOf(theOldestAnimal.getClass().getSimpleName())); //todo caterpillar shows as animal
            System.out.println("The oldest animal is: " + theOldestAnimal.getProperties().getType().getIcon() + theOldestAnimalName
                    + ". Lives already " + theOldestAnimal.getDaysAliveCounter() + " days.");
        }
    }

    private void printCountOfEachTypeOfAnimal() {
        List<Animal> allAnimals = new ArrayList<>();
        for (Cell[] value : cells) {
            for (Cell cell : value) {
                allAnimals.addAll(cell.getAnimalsOnCellCopy());
            }
        }
        for (AnimalType type : AnimalType.values()) {
            int counter = 0;
            for (Animal animal : allAnimals) {
                AnimalType animalType = animal.getProperties().getType();
                if (type.equals(animalType)) {
                    counter++;
                }
            }
            System.out.print(type.getIcon()+ ":" + counter + " ");
        }
    }

    private Animal getTheOldestAnimal() {
        Cell startedCell = Arrays.stream(cells)
                .flatMap(array -> Arrays.stream(array)
                        .filter(cell -> cell.getAmountOfAnimalsOnCell() > 0))
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
}
