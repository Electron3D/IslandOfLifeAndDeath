package com.electron3d.model.creatures;

import com.electron3d.model.island.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public abstract class Animal implements Callable<Animal> {
    private static final int AGE_OF_BECOMING_ADULT = 10;
    private static final int MAX_NUMBER_OF_DAYS_WITHOUT_FOOD = 4;
    private static final int STARVATION_MULTIPLIER = 5;
    private final AnimalSpecification specification;
    protected final double fullEnoughLevel;
    protected final int startedHealthPoints;
    protected int currentHealthPoints;
    private int starvation;
    private int daysAliveCounter;
    private boolean isAdult;
    private volatile boolean isDead;
    private boolean walkedToday;
    private boolean bredSuccessfullyToday;
    private Cell previousLocation;
    private Cell currentLocation;
    public Animal(AnimalSpecification specification, Cell currentLocation) {
        this.specification = specification;
        this.fullEnoughLevel = specification.getAmountOfFoodToBeFull();
        this.startedHealthPoints = (int) ((specification.getWeight() - specification.getAmountOfFoodToBeFull()) * 1000);
        this.currentHealthPoints = startedHealthPoints;
        this.currentLocation = currentLocation;
    }

    @Override
    public Animal call() {
        if (!walkedToday && !isDead) {
            tryToCompleteDailyGoals();
        }
        return this;
    }

    /**
     *  Checking animal's state, launch fatigue effect
     *  and methods that needs to satisfy animal's needs
     *  for success breeding
     */
    private void tryToCompleteDailyGoals() {
        boolean success = roamInSearchOfFood();
        feelHunger();
        if (currentHealthPoints <= 0) {
            isDead = die();
        }
        walkedToday = true;
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
        int numberOfStepsLeft = specification.getRange();
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

    private Cell chooseDirection() {
        List<Cell> possibleWays = currentLocation.getPossibleWays();
        Cell destinationCell = possibleWays.get(new Random().nextInt(0, possibleWays.size()));
        if (destinationCell != previousLocation) {
            List<Animal> sameTypeAnimals = destinationCell.getAmountOfAnimalsOnCellForType(specification.getType());
            if (sameTypeAnimals == null) {
                sameTypeAnimals = new ArrayList<>(0);
            }
            if (sameTypeAnimals.size() < specification.getBoundOnTheSameField()) {
                return destinationCell;
            } else {
                return this.currentLocation;
            }
        } else {
            return chooseDirection();
        }
    }

    private void walk(Cell destinationCell) {
        this.currentLocation.deleteAnimal(this);
        previousLocation = currentLocation;
        currentLocation = destinationCell;
        this.currentLocation.addAnimal(this);
    }

    private void feelHunger() {
        currentHealthPoints = currentHealthPoints - starvation * STARVATION_MULTIPLIER;
    }

    /**
     * The animal has a chance to reset scale of starvation to 0 or just to one point below.
     * Dice will be rolled to decide the fait.
     * <p>
     * - If the dice show 20 than this animal is the lucky one!
     * It has a second chance to live! Starvation is fully satisfied now, health restored.
     * <p>
     * - If the result will be more than 10 then this animal can live one more day...
     * <p>
     * - Otherwise sorry, but this animal is dead. Send a flower to it on its grave...
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
        List<Animal> animalsSameType = currentLocation.getAmountOfAnimalsOnCellForType(getSpecification().getType());
        int numberOfAnimalsSameType = animalsSameType.size();
        if (numberOfAnimalsSameType < specification.getBoundOnTheSameField()) {
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
    public AnimalSpecification getSpecification() {
        return specification;
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
