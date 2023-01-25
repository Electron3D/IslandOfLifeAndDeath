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
        AnimalsConfig config = AnimalsConfig.getInstance();
        List<AnimalProperties> animalsProperties = config.getAnimalsProperties();
        AnimalFactory factory = new AnimalFactory();
        for (AnimalType animalType : animalTypes) {
            AnimalProperties animalProperties = config.getAnimalPropertiesForType(animalType, animalsProperties);
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

    public void growPlants() {
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Cell cell = cells[y][x];
                cell.growPlants();
            }
        }
    }
    public boolean live() {
        if (!checkIsSmbAlive()) {
            return true;
        }
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                Cell cell = cells[y][x];
                if (cell.getAmountOfAnimalsOnCell() <= 0) {
                    continue;
                }
                cell.doAnimalStuff();
                cell.decomposeTheCorpses();
                cell.setNewDay();
            }
        }
        return false;
    }

    private boolean checkIsSmbAlive() {
        return Arrays
                .stream(cells)
                .parallel()
                .flatMap(cellsRow -> Arrays
                        .stream(cellsRow)
                        .map(Cell::getAmountOfAnimalsOnCell))
                .reduce(Integer::sum).orElse(0) > 0;
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



