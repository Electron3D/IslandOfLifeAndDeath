package com.electron3d.model.creatures;

import com.electron3d.model.island.Cell;

import java.util.*;
import java.util.stream.Stream;

public abstract class Animal {
    private final AnimalProperties properties;
    protected final double fullEnoughLevel;
    protected final int startedHealthPoints;
    protected int currentHealthPoints;
    private int starvation;
    private int daysAliveCounter;
    private boolean isAdult;
    private boolean isDead;
    private Cell previousLocation;
    private Cell currentLocation;

    public Animal(AnimalProperties properties, Cell currentLocation) {
        this.properties = properties;
        this.fullEnoughLevel = properties.getAmountOfFoodToBeFull() / 3;
        this.startedHealthPoints = (int) (properties.getWeight() - properties.getAmountOfFoodToBeFull() * 1000);
        this.currentHealthPoints = startedHealthPoints;
        this.currentLocation = currentLocation;
    }

    /**
     * Collect the results of the day from trying to satisfyNeeds method
     * that depend on if the animal still alive or die and if it bred successfully.
     */
    public boolean liveADay() {
        boolean success = roamInSearchOfFood();
        feelHunger();
        if (currentHealthPoints <= 0) {
            isDead = die();
        }
        if (success) {
            if (isAdult) {
                return breed();
            } else {
                return false;
            }
        } else {
            if (starvation >= 7) {
                isDead = die();
            } else {
                starvation++;
            }
        }
        if (!isDead) {
            growUp();
        }
        return false;
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

    private List<Eatable> getFoodListFromCell() {
        return Stream.concat(
                currentLocation.animalsOnTheCell.stream()
                        .filter(x -> x instanceof Eatable)
                        .map(x -> (Eatable) x),
                currentLocation.plantsOnTheCell.stream()
                        .map(x -> (Eatable) x)
        ).toList();
    }

    public abstract boolean eat(Eatable food);

    public abstract Eatable findFood(List<Eatable> foodList);

    public void walk(Cell destinationCell) {
        destinationCell.addAnimal(this);
        currentLocation.deleteAnimal(this);
        previousLocation = currentLocation;
        currentLocation = destinationCell;
        System.out.println(this + " move from " + previousLocation + " to " + currentLocation);
    }

    private Cell chooseDirection() {
        Cell destinationCell = currentLocation.possibleWays.get(new Random().nextInt(0, currentLocation.possibleWays.size()));
        if (destinationCell != previousLocation) {
            return destinationCell;
        } else {
            return chooseDirection();
        }
    }

    private void feelHunger() {
        currentHealthPoints = currentHealthPoints - starvation * 2;
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
        List<Animal> animalsSameType = currentLocation.animalsOnTheCell.parallelStream()
                .filter(a -> a.properties.getType().equals(this.properties.getType()))
                .toList();
        int numberOfAnimalsSameType = animalsSameType.size();
        if (numberOfAnimalsSameType < properties.getBoundOnTheSameField()) {
            return animalsSameType.parallelStream().filter(x -> x.isAdult).count() > 2;
        } else {
            return false;
        }
    }

    private void growUp() {
        daysAliveCounter++;
        if (daysAliveCounter > 7) {
            isAdult = true;
        }
    }

    public AnimalProperties getProperties() {
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
}
