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

    public Animal(AnimalProperties properties, Cell location) {
        this.properties = properties;
        this.location = location;
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
        double amountOfFoodEnoughToBeSatisfied = properties.getAmountOfFoodToBeFull() / 2;
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
        if (this instanceof HerbivoresAndCaterpillarEatingAnimal) {
            Random random = new Random();
            if (random.nextInt(0, 2) == 1) {
                currentSatisfactionLevel = eat((Eatable) location.animalsOnTheCell.stream()
                        .filter(x -> x instanceof Caterpillar)
                        .findAny()
                        .orElseThrow());
            } else {
                currentSatisfactionLevel = eat(location.plantsOnTheCell
                        .stream()
                        .findAny()
                        .orElseThrow());
            }
        } else if (this instanceof PredatorAnimal) {
            currentSatisfactionLevel = eat((Eatable) location.animalsOnTheCell
                    .stream()
                    .filter(x -> x instanceof Eatable && x != this)
                    .findAny()
                    .orElseThrow());
        } else if (this instanceof HerbivoresAnimal) {
            currentSatisfactionLevel = eat(location.plantsOnTheCell
                    .stream()
                    .findAny()
                    .orElseThrow());
        }
        return currentSatisfactionLevel;
    }

    public abstract double eat(Eatable food); /*{
        //todo
        // check chances and if success delete food,
        // check food.restoreHP() < this.properties.getAmountOfFoodToBeFull()
        // and return amount of eaten food
        return 1.0;
    }*/

    public void walk(Cell destinationCell) {
        location = destinationCell;

        //todo
    }

    private Cell chooseDirection() {
        /*return location.possibleWays.stream()
                .filter(x -> Stream.concat(x.animalsOnTheCell.stream(), x.plantsOnTheCell.stream()).anyMatch(y -> y instanceof Eatable))
                .findAny().orElseThrow(); //todo redo*/
        return this.location;
    }

    private boolean die() {
        Random dice = new Random();
        int savingThrow = dice.nextInt(1, 21);
        if (savingThrow == 20) {
            starvation = 0;
            //System.out.println("Oh! This " + this.properties.getType() + " is the lucky one! You have a second chance to live! Starvation is fully satisfied now.");
            return false;
        } else if (savingThrow > 10) {
            starvation--;
            //System.out.println("This " + this.properties.getType() + " can live one more day..");
            return false;
        } else {
            //System.out.println("Sorry, this " + this.properties.getType() + " is dead. Send a flower to it on its grave..");
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
