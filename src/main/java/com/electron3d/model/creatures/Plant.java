package com.electron3d.model.creatures;

import com.electron3d.model.island.Cell;

import java.util.Random;

public class Plant implements Eatable {
    public final static String ICON = "\uD83C\uDF3F";
    private final PlantProperties properties;
    private final Cell location;
    private double numberOfBerries;
    private int plantGrowth;

    public Plant(PlantProperties properties, Cell location) {
        this.properties = properties;
        this.location = location;
        this.numberOfBerries = properties.getWeight() * 20;
    }

    public int grow() {
        if (plantGrowth >= 1) {
            int boundOnTheSameField = properties.getBoundOnTheSameField();
            int amount = location.getAmountOfPlantsOnTheCell();
            if (amount > 0 && amount < boundOnTheSameField) {
                Random random = new Random();
                int numberOfNewGrownPlants = random.nextInt(0, 10);
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
        numberOfBerries--;
        if (numberOfBerries == 0) {
            this.location.deletePlant(this);
        }
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
