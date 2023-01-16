package com.electron3d.model.creatures;

import com.electron3d.model.creatures.animals.Herbivores;
import com.electron3d.model.creatures.animals.Predatory;
import com.electron3d.model.creatures.animals.herbivores.Duck;
import com.electron3d.model.island.Field;

import java.util.NoSuchElementException;

public abstract class Animal {
    private Field location;

    public Animal(Field location) {
        this.location = location;
    }

    public void liveADay() {
        int numberOfStepsLeft = 3; //todo add speed here
        while (true) {
            try {
                if (this instanceof Duck) {
                    //todo
                    System.out.println("Я утка");
                } else if (this instanceof Predatory) {
                    eat((Eatable) location.animalsOnTheField.stream().filter(x -> x instanceof Eatable).findAny().orElseThrow()); //todo исключить текущий объект
                    System.out.println("Я хищник");
                } else if (this instanceof Herbivores) {
                    eat(location.plantsOnTheField.stream().findAny().orElseThrow());
                    System.out.println("Я травоядное");
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
    }

    public void eat(Eatable food) {

    }

    public void breed() {

    }

    public void walk() {
        Field destinationField = chooseDirection();
        changeLocation(destinationField);
    }

    private Field chooseDirection() {
        return location.possibleWays.get(0);
    }

    private void changeLocation(Field destinationField) {

    }
}
