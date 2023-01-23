package com.electron3d.model.creatures;

import com.electron3d.model.island.Cell;

import java.util.Random;

public class Plant implements Eatable {
    private final PlantProperties properties;

    private final Cell location;
    private int plantGrowth;
    public Plant(PlantProperties properties, Cell location) {
        this.properties = properties;
        this.location = location;
    }

    public int grow() {
        if (plantGrowth >= 3) {
            int boundOnTheSameField = properties.getBoundOnTheSameField();
            int amount = location.getAmountOfPlantsOnTheField();
            if (amount > 0 && amount < boundOnTheSameField) {
                Random random = new Random();
                int numberOfNewGrownPlants = random.nextInt(0, 3);
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
            plantGrowth++;
            return 0;
        }
    }

    @Override
    public double restoreHP() {
        return properties.getWeight();
    }

    public Cell getLocation() {
        return location;
    }

    public PlantProperties getProperties() {
        return properties;
    }

    public void setPlantGrowth(int plantGrowth) {
        this.plantGrowth = plantGrowth;
    }
}
