package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.*;

import java.util.*;

public class Island {
    private final int xDimension;
    private final int yDimension;
    private final Cell[][] cells;
    private final List<AnimalType> animalTypes;

    public Island(int xDimension, int yDimension, List<AnimalType> animalTypes) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.cells = new Cell[yDimension][xDimension];
        this.animalTypes = animalTypes;
    }

    public void initCells() {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Cell cell = new Cell(x, y);
                cells[y][x] = cell;
                initPlants(cell);
                initAnimals(cell);
            }
        }
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Cell cell = cells[y][x];
                cell.addPossibleWays(initPossibleWays(cell));
            }
        }
    }

    private void initPlants(Cell cell) {
        Random startingPlantsCountChooser = new Random();
        PlantProperties properties = AnimalsConfig.getInstance().getPlantProperties();
        int amountOfPlantsOnTheField = startingPlantsCountChooser.nextInt(properties.getBoundOnTheSameField() + 1);
        for (int i = 0; i < amountOfPlantsOnTheField; i++) {
            Plant newPlant = new Plant(properties, cell);
            newPlant.setPlantGrowth(3);
            cell.addPlant(newPlant);
        }
    }

    private void initAnimals(Cell cell) {
        Random startingAnimalsCountChooser = new Random();
        List<AnimalProperties> animalsProperties = AnimalsConfig.getInstance().getAnimalsProperties();
        AnimalFactory factory = new AnimalFactory();
        for (AnimalType animalType : animalTypes) {
            AnimalProperties animalProperties = animalsProperties
                    .stream()
                    .filter(x -> animalType.equals(x.getType()))
                    .findFirst()
                    .orElseThrow();
            int startingAnimalsCount = startingAnimalsCountChooser.nextInt(animalProperties.getBoundOnTheSameField() + 1);
            for (int i = 0; i < startingAnimalsCount; i++) {
                Animal animal = factory.createAnimal(animalType, cell);
                animal.setAdult(true);
                cell.addAnimal(animal);
            }
        }
    }

    private List<Cell> initPossibleWays(Cell cell) {
        List<Cell> possibleWays = new ArrayList<>();
        int i0 = cell.getX();
        int j0 = cell.getY();
        int height = cells.length;
        int width = cells[0].length;
        for (int i = i0 - 1; i <= i0 + 1; ++i) {
            for (int j = j0 - 1; j <= j0 + 1; ++j) {
                if (0 <= i && i < height && 0 <= j && j < width && (i != i0 || j != j0)) {
                    possibleWays.add(cells[j][i]);
                }
            }
        }
        return possibleWays;
    }

    public void live() {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Cell cell = cells[y][x];
                if (cell.getAmountOfAnimalsOnTheCell() <= 0) {
                    continue;
                }
                cell.growPlants();
                cell.doAnimalStuff();
                cell.decomposeTheCorpses();
                cell.setNewDay();
            }
        }
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

    @Override
    public String toString() {
        StringBuilder cellsToString = new StringBuilder();
        for (Cell[] cellsOnTheSameMeridian : cells) {
            for (Cell cell : cellsOnTheSameMeridian) {
                cellsToString.append(cell.toString()).append(" ");
                if (cell.getX() < 10) {
                    cellsToString.append(" ");
                }
                if (cell.getY() < 10) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfPlantsOnTheCell() < 10) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfPlantsOnTheCell() < 100) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfAnimalsOnTheCell() < 10) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfAnimalsOnTheCell() < 100) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfAnimalsOnTheCell() < 1000) {
                    cellsToString.append(" ");
                }
            }
            cellsToString.append("\n");
        }
        return "Island\n{" +
                "parallelLength=" + xDimension +
                ", meridianLength=" + yDimension +
                ", cells=\n" + cellsToString +
                "}\n";
    }
}



