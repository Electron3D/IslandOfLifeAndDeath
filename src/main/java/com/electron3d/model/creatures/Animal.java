package com.electron3d.model.creatures;

import com.electron3d.model.island.Cell;

import java.util.*;
import java.util.concurrent.Callable;

public abstract class Animal implements Callable<Animal> {
    private static final int AGE_OF_BECOMING_ADULT = 10;
    private static final int MAX_NUMBER_OF_DAYS_WITHOUT_FOOD = 4;
    private static final int STARVATION_MULTIPLIER = 4;
    private final AnimalSpecification properties;
    protected final double fullEnoughLevel;
    protected final int startedHealthPoints;
    protected int currentHealthPoints;
    private int starvation;
    private int daysAliveCounter;
    private boolean isAdult;
    private boolean isDead;
    private boolean walkedToday;
    private boolean bredSuccessfullyToday;
    private Cell previousLocation;

    private Cell currentLocation;
    public Animal(AnimalSpecification properties, Cell currentLocation) {
        this.properties = properties;
        this.fullEnoughLevel = properties.getAmountOfFoodToBeFull();
        this.startedHealthPoints = (int) ((properties.getWeight() - properties.getAmountOfFoodToBeFull()) * 1000);
        this.currentHealthPoints = startedHealthPoints;
        this.currentLocation = currentLocation;
    }

    @Override
    public Animal call() {
        tryToBreed();
        return this;
    }

    /**
     *  Checking animal's state, launch fatigue effect
     *  and methods that needs to satisfy animal's needs
     *  for success breeding
     */
    public void tryToBreed() {
        if (walkedToday) {
            return;
        }
        walkedToday = true;
        if (isDead) {
            return;
        }
        boolean success = roamInSearchOfFood();
        feelHunger();
        if (currentHealthPoints <= 0) {
            isDead = die();
        }
        if (success && !isDead) {
            growUp();
            if (isAdult) {
                bredSuccessfullyToday = breed();
            }
        } else {
            if (starvation >= MAX_NUMBER_OF_DAYS_WITHOUT_FOOD) {
                isDead = die();
            } else {
                starvation++;
            }
            if (!isDead) {
                growUp();
            }
        }
    }

    /**
     * An animal keep roaming from cell to cell until it finds food or the number of steps left ends.
     */
    private boolean roamInSearchOfFood() {
        int numberOfStepsLeft = properties.getRange();
        boolean success;
        while (true) {
            Eatable food = findFood(getFoodListFromCell());
            if (food != null) {
                success = eat(food);
                return success;
            } else {
                if (numberOfStepsLeft > 0) {
                    walk(chooseDirection());
                    numberOfStepsLeft--;
                } else break;
            }
        }
        return false;
    }

    protected abstract List<Eatable> getFoodListFromCell();

    protected abstract boolean eat(Eatable food);

    protected abstract Eatable findFood(List<Eatable> foodList);

    public void walk(Cell destinationCell) {
        this.currentLocation.deleteAnimal(this);
        previousLocation = currentLocation;
        currentLocation = destinationCell;
        this.currentLocation.addAnimal(this);
    }

    private Cell chooseDirection() {
        List<Cell> possibleWays = currentLocation.getPossibleWays();
        Cell destinationCell = possibleWays.get(new Random().nextInt(0, possibleWays.size()));
        if (destinationCell != previousLocation) {
            if (destinationCell.getAmountOfAnimalsOnCell() < getProperties().getBoundOnTheSameField()) {
                return destinationCell;
            } else {
                return this.currentLocation;
            }
        } else {
            return chooseDirection();
        }
    }

    private void feelHunger() {
        currentHealthPoints = currentHealthPoints - starvation * STARVATION_MULTIPLIER;
    }

    /**
     * The animal has a chance to reset scale of starvation to 0 or just to one point below.
     * Dice will be rolled to decide the fait.
     * If the dice show 20 than this animal is the lucky one! It has a second chance to live! Starvation is fully satisfied now, health restored.
     * If th result will be more than 10 than this animal can live one more day...
     * Otherwise sorry, but this animal is dead. Send a flower to it on its grave...
     */
    private boolean die() {
        Random dice = new Random();
        int savingThrow = dice.nextInt(1, 21);
        if (savingThrow == 20) {
            currentHealthPoints = startedHealthPoints;
            starvation = 0;
            return false;
        } else if (savingThrow > 10) {
            currentHealthPoints = 1;
            starvation--;
            return false;
        } else {
            return true;
        }
    }

    private boolean breed() {
        List<Animal> animalsSameType = currentLocation.getAnimalsOnCell()
                .stream()
                .filter(a -> a.properties.getType().equals(this.properties.getType()))
                .toList();
        int numberOfAnimalsSameType = animalsSameType.size();
        if (numberOfAnimalsSameType < properties.getBoundOnTheSameField()) {
            return animalsSameType
                    .stream()
                    .filter(x -> x.isAdult)
                    .count() > 2;
        } else {
            return false;
        }
    }

    private void growUp() {
        daysAliveCounter++;
        if (daysAliveCounter > AGE_OF_BECOMING_ADULT) {
            isAdult = true;
        }
    }
    public AnimalSpecification getProperties() {
        return properties;
    }

    public Cell getCurrentLocation() {
        return currentLocation;
    }

    public int getDaysAliveCounter() {
        return daysAliveCounter;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setWalkedTodayFalse() {
        this.walkedToday = false;
    }

    public boolean isBredSuccessfullyToday() {
        return bredSuccessfullyToday;
    }

    public void setBredSuccessfullyTodayFalse() {
        this.bredSuccessfullyToday = false;
    }
}
