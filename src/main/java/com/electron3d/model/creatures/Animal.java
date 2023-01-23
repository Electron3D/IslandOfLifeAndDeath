package com.electron3d.model.creatures;

import com.electron3d.model.creatures.animals.HerbivoresAndCaterpillarEatingAnimal;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.creatures.animals.PredatorAnimal;
import com.electron3d.model.creatures.animals.herbivores.Caterpillar;
import com.electron3d.model.island.Cell;

import java.util.*;
import java.util.stream.Stream;

public abstract class Animal {
    private final AnimalProperties properties;
    private Cell location;
    private int starvation;
    private int daysAliveCounter;
    private boolean isAdult;
    protected final double amountOfFoodEnoughToBeSatisfied;

    public Animal(AnimalProperties properties, Cell location) {
        this.properties = properties;
        this.location = location;
        this.amountOfFoodEnoughToBeSatisfied = properties.getAmountOfFoodToBeFull() / 2;
    }

    /**
     *  Trying to satisfy needs and collect the results of the day
     *  that depend on if the animal still alive or die and if it bred successfully.
     */
    public Map<String, Boolean> liveADay() {
        Map<String, Boolean> resultsOfTheDay = new HashMap<>();
        boolean isSatisfied = satisfyNeeds();
        if (isSatisfied) {
            if (isAdult) {
                resultsOfTheDay.put("breedSucceed", breed());
            } else {
                resultsOfTheDay.put("breedSucceed", false);
            }
            resultsOfTheDay.put("diedThisDay", false);
        } else {
            resultsOfTheDay.put("breedSucceed", false);
            if (starvation >= 7) {
                resultsOfTheDay.put("diedThisDay", die());
            } else {
                resultsOfTheDay.put("diedThisDay", false);
                starvation++;
            }
        }
        if (!resultsOfTheDay.get("diedThisDay")) {
            growUp();
        }
        return resultsOfTheDay;
    }

    /**
     *  In this method an animal tries to eat and if there isn't food it tries to find it in another field.
     */
    private boolean satisfyNeeds() {
        int numberOfStepsLeft = properties.getRange();
        boolean isSatisfied = false;
        double currentSatisfactionLevel = 0.0;
        while (true) {
            try {
                int attemptsToEat = 0;
                while (attemptsToEat <= 3) {
                    double resultsOfFindingFood = tryToFindSomethingEatable();
                    currentSatisfactionLevel = currentSatisfactionLevel + resultsOfFindingFood;
                    attemptsToEat++;
                    if (currentSatisfactionLevel > amountOfFoodEnoughToBeSatisfied) {
                        isSatisfied = true;
                        break;
                    }
                }
                break;
            } catch (NoSuchElementException e) {
                if (numberOfStepsLeft == 0) {
                    break;
                } else {
                    walk(chooseDirection());
                    numberOfStepsLeft--;
                }
            }
        }
        return isSatisfied;
    }

    private double tryToFindSomethingEatable() throws NoSuchElementException {
        double currentSatisfactionLevel = 0;
        List<Eatable> eatableList = Stream.concat(
                location.animalsOnTheCell.parallelStream()
                        .filter(x -> x instanceof Eatable)
                        .map(x -> (Eatable) x),
                location.plantsOnTheCell.parallelStream()
                        .map(x -> (Eatable) x)
                ).toList();
        currentSatisfactionLevel = eat(eatableList);
        return currentSatisfactionLevel;
    }

    public abstract double eat(List<Eatable> food);
        // todo
        // check chances and if success delete food,
        // check food.restoreHP() < this.properties.getAmountOfFoodToBeFull()
        // and return amount of eaten food

    public void walk(Cell destinationCell) {
        destinationCell.addAnimal(this);
        location.deleteAnimal(this);
        location = destinationCell;
    }

    private Cell chooseDirection() {
        /*return location.possibleWays.stream()
                .filter(x -> Stream.concat(x.animalsOnTheCell.stream(), x.plantsOnTheCell.stream()).anyMatch(y -> y instanceof Eatable))
                .findAny().orElseThrow();*/ //todo add random to choose the way
        return location;
    }

    /**
     *  The animal has a chance to reset scale of starvation to 0 or just to one point below.
     *  Dice will be rolled to decide the fait.
     *  If the dice show 20 than this animal is the lucky one! It has a second chance to live! Starvation is fully satisfied now.
     *  If th result will be more than 10 than this animal can live one more day..
     *  Otherwise sorry, but this animal is dead. Send a flower to it on its grave..
     */
    private boolean die() {
        Random dice = new Random();
        int savingThrow = dice.nextInt(1, 21);
        if (savingThrow == 20) {
            starvation = 0;
            return false;
        } else if (savingThrow > 10) {
            starvation--;
            return false;
        } else {
            return true;
        }
    }

    private boolean breed() {
        List<Animal> animalsSameType = location.animalsOnTheCell.parallelStream()
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

    public Cell getLocation() {
        return location;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public int getDaysAliveCounter() {
        return daysAliveCounter;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}
