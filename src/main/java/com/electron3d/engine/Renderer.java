package com.electron3d.engine;

import com.electron3d.model.IslandSimulation;
import com.electron3d.model.config.IslandSimulationConfig;
import com.electron3d.model.creatures.Animal;
import com.electron3d.model.creatures.AnimalType;
import com.electron3d.model.island.Cell;
import com.electron3d.model.island.Island;

import java.util.*;
import java.util.stream.Collectors;

import static com.electron3d.model.creatures.PlantSpecification.ICON;

public class Renderer {
    private final Island island;
    private final Cell[][] cells;

    public Renderer(IslandSimulation simulation) {
        this.island = simulation.getIsland();
        this.cells = island.getCells();
    }

    public void printStartSimulationConditions() {
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        double delayMultiplier = islandSimulationConfig.getTimeDelayMultiplier();
        int maxTimer = islandSimulationConfig.getMaxTimeOfSimulationInSeconds();
        System.out.println("Starting condition:");
        System.out.println("Simulation will continue to " + maxTimer + " day.");
        System.out.println("Time delay multiplier is set to x" + delayMultiplier);
    }

    public void printSimulationStateForDay(int timer) {
        System.out.println("\n\n\n");
        printCurrentDay(timer);
        getSnapshotOfTheIsland();
        printStats();
    }

    public void printCurrentDay(int timer) {
        IslandSimulationConfig islandSimulationConfig = IslandSimulationConfig.getInstance();
        double timeFlowSpeedMultiplier = islandSimulationConfig.getTimeDelayMultiplier();
        System.out.println("Day: " + timer + " Delay - " + timeFlowSpeedMultiplier + " sec");
    }

    public void getSnapshotOfTheIsland() {
        System.out.println(island);
    }

    public void printSimulationResults(int timer) {
        System.out.println("\n\n\n");
        if (timer >= IslandSimulationConfig.getInstance().getMaxTimeOfSimulationInSeconds()) {
            System.out.print("Time is over! Final results:\n");
        } else {
            System.out.print("All animals extinct! Final results:\n");
        }
        System.out.println("The simulation ended on day " + timer + "\n");
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
            String theOldestAnimalName = theOldestAnimal.toString().substring(theOldestAnimal.toString().indexOf(theOldestAnimal.getClass().getSimpleName()));
            System.out.println("The oldest animal is: " + theOldestAnimal.getSpecification().getType().getIcon() + theOldestAnimalName
                    + ". Lives already " + theOldestAnimal.getDaysAliveCounter() + " days in cell " + theOldestAnimal.getCurrentLocation());
            Set<AnimalType> animalTypesOnTheSameCell = theOldestAnimal
                    .getCurrentLocation()
                    .getAnimalsOnCell()
                    .stream()
                    .map(animal -> animal.getSpecification().getType())
                    .collect(Collectors.toSet());
            System.out.println("The oldest animal share the cell with: " + animalTypesOnTheSameCell);
        }
    }

    private void printCountOfEachTypeOfAnimal() {
        List<Animal> allAnimals = new ArrayList<>();
        for (Cell[] value : cells) {
            for (Cell cell : value) {
                allAnimals.addAll(cell.getAnimalsOnCell());
            }
        }
        for (AnimalType type : AnimalType.values()) {
            int counter = 0;
            for (Animal animal : allAnimals) {
                AnimalType animalType = animal.getSpecification().getType();
                if (type.equals(animalType)) {
                    counter++;
                }
            }
            System.out.print(type.getIcon()+ ":" + counter + " ");
        }
    }

    private Animal getTheOldestAnimal() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .flatMap(cell -> cell.getAnimalsOnCell().stream())
                .parallel()
                .max(Comparator.comparingInt(Animal::getDaysAliveCounter))
                .orElse(null);
    }
}
