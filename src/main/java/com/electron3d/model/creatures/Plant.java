package com.electron3d.model.creatures;

import com.electron3d.model.island.Cell;

import java.util.Random;

public class Plant implements Eatable {
    private final static int LEVEL_TO_START_GROWING = 2;
    private final static int MULTIPLIER_OF_BERRIES_PER_PLANT = 20;
    private final static int MAX_NUMBER_OF_NEW_GROWN_PLANTS = 10;
    private final PlantSpecification properties;
    private final Cell location;
    private double numberOfBerries;
    private int level;

    public Plant(PlantSpecification properties, Cell location) {
        this.properties = properties;
        this.location = location;
        this.numberOfBerries = properties.getWeight() * MULTIPLIER_OF_BERRIES_PER_PLANT;
    }

    public int grow() {
        if (level >= LEVEL_TO_START_GROWING) {
            int boundOnTheSameField = properties.getBoundOnTheSameCell();
            int amount = location.getAmountOfPlantsOnCell();
            if (amount > 0 && amount < boundOnTheSameField) {
                Random random = new Random();
                int numberOfNewGrownPlants = random.nextInt(0, MAX_NUMBER_OF_NEW_GROWN_PLANTS);
                int newAmount = amount + numberOfNewGrownPlants;
                if (newAmount > boundOnTheSameField) {
                    return boundOnTheSameField - amount;
                } else {
                    return numberOfNewGrownPlants;
                }
            } else {
                return 0;
            }
        } else {
            level++;
            return 0;
        }
    }

    @Override
    public double restoreHP() {
        numberOfBerries--;
        if (numberOfBerries == 0) {
            this.location.deletePlant(this);
        }
        return properties.getWeight();
    }

    public Cell getLocation() {
        return location;
    }

    public PlantSpecification getProperties() {
        return properties;
    }

    public void setPlantToGrowthStage() {
        this.level = LEVEL_TO_START_GROWING;
    }
}
