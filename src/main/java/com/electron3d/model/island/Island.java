package com.electron3d.model.island;

import com.electron3d.model.config.AnimalsConfig;
import com.electron3d.model.creatures.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        for (Cell[] cellsRow : cells) {
            for (Cell cell : cellsRow) {
                cell.addPossibleWays(initPossibleWays(cell));
            }
        }
    }

    private void initPlants(Cell cell) {
        Random startingPlantsCountChooser = new Random();
        PlantSpecification properties = AnimalsConfig.getInstance().getPlantSpecification();
        int boundOnTheSameCell = properties.getBoundOnTheSameCell();
        int amountOfPlantsOnTheField = startingPlantsCountChooser.nextInt(boundOnTheSameCell / 3, boundOnTheSameCell + 1);
        for (int i = 0; i < amountOfPlantsOnTheField; i++) {
            Plant newPlant = new Plant(properties, cell);
            newPlant.setPlantToGrowthStage();
            cell.addPlant(newPlant);
        }
    }

    private void initAnimals(Cell cell) {
        Random startingAnimalsCountChooser = new Random();
        AnimalsConfig config = AnimalsConfig.getInstance();
        AnimalFactory factory = new AnimalFactory();
        for (AnimalType animalType : animalTypes) {
            AnimalSpecification animalSpecification = config.getAnimalSpecificationForType(animalType);
            int startingAnimalsCount = startingAnimalsCountChooser.nextInt(animalSpecification.getBoundOnTheSameField() + 1);
            for (int i = 0; i < startingAnimalsCount; i++) {
                Animal animal = factory.createAnimal(animalType, cell);
                animal.setAdult(true);
                cell.addAnimal(animal);
            }
        }
    }

    private List<Cell> initPossibleWays(Cell cell) {
        List<Cell> possibleWays = new ArrayList<>();
        int y0 = cell.getY();
        int x0 = cell.getX();
        int height = cells.length;
        int width = cells[0].length;
        for (int y = y0 - 1; y <= y0 + 1; ++y) {
            for (int x = x0 - 1; x <= x0 + 1; ++x) {
                if (0 <= y && y < height && 0 <= x && x < width && (y != y0 || x != x0)) {
                    possibleWays.add(cells[y][x]);
                }
            }
        }
        return possibleWays;
    }

    public boolean liveADay() {
        if (!checkIsSmbAlive()) {
            return true;
        }
        List<Plant> plants = collectPlantsFromCells();
        growPlants(plants);
        List<Animal> animals = collectAnimalsFromCells();
        doAnimalStuffParallel(animals);
        decomposeTheCorpses(animals);
        return false;
    }

    private void growPlants(List<Plant> plants) {
        PlantSpecification properties = AnimalsConfig.getInstance().getPlantSpecification();
        for (Plant plant : plants) {
            Cell plantCell = plant.getLocation();
            List<Plant> newGrownPlants = new ArrayList<>();
            int numberOfNewGrownPlants = plant.grow();
            for (int j = 0; j < numberOfNewGrownPlants; j++) {
                newGrownPlants.add(new Plant(properties, plantCell));
            }
            plantCell.getPlantsOnCell().addAll(newGrownPlants);
        }
    }

    private void doAnimalStuffParallel(List<Animal> animals) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            executorService.invokeAll(animals);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
        boolean isTerminated;
        try {
            isTerminated = executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!isTerminated) {
            throw new RuntimeException("Timeout ends, executor still isn't terminated.");
        }
        for (Animal animal : animals) {
            if (!animal.isDead() && animal.isBredSuccessfullyToday()) {
                Cell animalCell = animal.getCurrentLocation();
                AnimalType animalType = animal.getSpecification().getType();
                int boundOfThisTypeAnimalOnCell = animal.getSpecification().getBoundOnTheSameField();
                long amountOfAnimalsThisTypeOnCell = animalCell.getAnimalsOnCellOfType(animalType).size();
                if (amountOfAnimalsThisTypeOnCell + 1 < boundOfThisTypeAnimalOnCell) {
                    AnimalFactory factory = new AnimalFactory();
                    Animal animalToAdd = factory.createAnimal(animalType, animalCell);
                    animalCell.releaseNewBornAnimal(animalToAdd);
                }
            }
        }
    }

    private void decomposeTheCorpses(List<Animal> animals) {
        animals.stream()
                .filter(Animal::isDead)
                .forEach(animal -> animal.getCurrentLocation().buryAnimal(animal));
    }

    private boolean checkIsSmbAlive() {
        return Arrays
                .stream(cells)
                .parallel()
                .flatMap(cellsRow -> Arrays
                        .stream(cellsRow)
                        .map(Cell::getAmountOfAnimalsOnCell))
                .reduce(Integer::sum)
                .orElse(0) > 0;
    }

    private List<Plant> collectPlantsFromCells() {
        List<Plant> plants = new ArrayList<>();
        for (Cell[] cellsRow : cells) {
            for (Cell cell : cellsRow) {
                plants.addAll(cell.getPlantsOnCell());
            }
        }
        return plants;
    }

    private List<Animal> collectAnimalsFromCells() {
        List<Animal> animals = new ArrayList<>();
        for (Cell[] cellsRow : cells) {
            for (Cell cell : cellsRow) {
                animals.addAll(cell.getAnimalsOnCell());
            }
        }
        return animals;
    }

    public Cell[][] getCells() {
        return cells;
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
                if (cell.getAmountOfPlantsOnCell() < 10) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfPlantsOnCell() < 100) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfAnimalsOnCell() < 10) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfAnimalsOnCell() < 100) {
                    cellsToString.append(" ");
                }
                if (cell.getAmountOfAnimalsOnCell() < 1000) {
                    cellsToString.append(" ");
                }
            }
            cellsToString.append("\n");
        }
        return "Island\n" +
                "Parallel length = " + xDimension +
                ", meridian length = " + yDimension +
                ", cells =\n" + cellsToString +
                "\n";
    }
}



