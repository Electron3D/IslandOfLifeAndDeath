package com.electron3d.model.creatures;

import com.electron3d.model.creatures.animals.HerbivoresAndCaterpillarEatingAnimal;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.creatures.animals.PredatorAnimal;
import com.electron3d.model.creatures.animals.herbivores.Caterpillar;
import com.electron3d.model.island.Field;

import java.util.NoSuchElementException;
import java.util.Random;

public abstract class Animal {
    private final AnimalProperties properties;
    private Field location;
    private int starvation;
    private int daysAliveCounter;

    public Animal(AnimalProperties properties, Field location) {
        this.properties = properties;
        this.location = location;
    }

    public boolean liveADay() {
        int numberOfStepsLeft = properties.getRange();
        boolean isSatisfied = false;
        double currentSatisfactionLevel = 0.0;
        while (!isSatisfied) {
            try {
                currentSatisfactionLevel = currentSatisfactionLevel + satisfyNeeds();
            } catch (NoSuchElementException e) {
                if (numberOfStepsLeft == 0) {
                    if (starvation >= 7) {
                        return die();
                    } else {
                        starvation++;
                    }
                    break;
                }
                walk();
                numberOfStepsLeft--;
            }
            if (currentSatisfactionLevel < properties.getAmountOfFoodToBeFull() / 2) {
                isSatisfied = true;
            }
        }
        if (isSatisfied) {
            breed();
        }
        daysAliveCounter++;
        return true;
    }

    private double satisfyNeeds() throws NoSuchElementException {
        double currentSatisfactionLevel = 0;
        if (this instanceof HerbivoresAndCaterpillarEatingAnimal) {
            //System.out.println("Я люблю гусениц и траву");
            //todo addRandom
            if (true) {
                currentSatisfactionLevel = eat((Eatable) location.animalsOnTheField.stream()
                        .filter(x -> x instanceof Caterpillar)
                        .findAny().orElseThrow());
            } else {
                currentSatisfactionLevel = eat(location.plantsOnTheField.stream().findAny().orElseThrow());
            }
        } else if (this instanceof PredatorAnimal) {
            //System.out.println("Я хищник");
            currentSatisfactionLevel = eat((Eatable) location.animalsOnTheField.stream()
                    .filter(x -> x instanceof Eatable && x != this)
                    .findAny().orElseThrow());
        } else if (this instanceof HerbivoresAnimal) {
            //System.out.println("Я травоядное");
            currentSatisfactionLevel = eat(location.plantsOnTheField.stream().findAny().orElseThrow());
        }
        return currentSatisfactionLevel;
    }

    public double eat(Eatable food) {
        //todo
        // check chances and if success delete food,
        // check food.restoreHP() < this.properties.getAmountOfFoodToBeFull()
        // and return amount of eaten food
        return 0.0;
    }

    public void walk() {
        Field destinationField = chooseDirection();
        changeLocation(destinationField);
    }

    private Field chooseDirection() {
        return location.possibleWays.get(0); //todo add random factor or another logic
    }

    private void changeLocation(Field destinationField) {
        //todo
    }

    private boolean die() {
        Random dice = new Random();
        int savingThrow = dice.nextInt(1,20);
        if (savingThrow == 20) {
            starvation = 0;
            System.out.println("Oh! This " + this.properties.getType() + " is the lucky one! You have a second chance to live! Starvation is fully satisfied now.");
            return false;
        } else if (savingThrow > 10) {
            starvation--;
            System.out.println("This " + this.properties.getType() + " can live one more day..");
            return false;
        } else {
            System.out.println("Sorry, this animal is dead. Send a flower to it on its grave..");
            return true;
        }
    }

    public void breed() {
        //todo add mechanic of growing up to start breeding
    }

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }

    public int getDaysAliveCounter() {
        return daysAliveCounter;
    }
}
