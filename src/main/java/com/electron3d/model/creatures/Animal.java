package com.electron3d.model.creatures;

import com.electron3d.model.creatures.animals.HerbivoresAndCaterpillarEatingAnimal;
import com.electron3d.model.creatures.animals.HerbivoresAnimal;
import com.electron3d.model.creatures.animals.PredatorAnimal;
import com.electron3d.model.creatures.animals.herbivores.Duck;
import com.electron3d.model.island.Field;

import java.util.NoSuchElementException;

public abstract class Animal {
    private final AnimalProperties properties;
    private Field location;

    public Animal(AnimalProperties properties, Field location) {
        this.properties = properties;
        this.location = location;
    }

    public void liveADay() {
        int numberOfStepsLeft = properties.getRange();
        while (true) {
            try {
                if (this instanceof HerbivoresAndCaterpillarEatingAnimal) {
                    //todo addRandom
                    if (true) {
                        ((HerbivoresAndCaterpillarEatingAnimal) this).accidentallyEatCaterpillar();
                    } else {
                        eat(location.plantsOnTheField.stream().findAny().orElseThrow());
                    }
                    //System.out.println("Я люблю гусениц и траву");
                } else if (this instanceof PredatorAnimal) {
                    eat((Eatable) location.animalsOnTheField.stream().filter(x -> x instanceof Eatable).findAny().orElseThrow()); //todo исключить текущий объект
                    //System.out.println("Я хищник");
                } else if (this instanceof HerbivoresAnimal) {
                    eat(location.plantsOnTheField.stream().findAny().orElseThrow());
                    //System.out.println("Я травоядное");
                }
                breed();
                break;
            } catch (NoSuchElementException e) {
                if (numberOfStepsLeft == 0) {
                    die();
                    break;
                }
                walk();
                numberOfStepsLeft--;
            }
        }
    }

    private void die() {
        //todo add starvation mechanic
    }

    public void eat(Eatable food) {
        //todo
    }

    public void breed() {
        //todo add mechanic of growing up to start breeding
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

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }
}
