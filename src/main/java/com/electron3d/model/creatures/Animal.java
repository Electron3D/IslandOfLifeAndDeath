package com.electron3d.model.creatures;

import com.electron3d.model.island.Field;

public abstract class Animal {
    private Field location;

    public Animal(Field location) {
        this.location = location;
    }
    public void doAnimalStuff() {
        eat((Eatable) location.animalsOnTheField.stream().filter(x -> x instanceof Eatable).findAny().orElseThrow());
        breed();
        walk();
    }
    public void eat(Eatable food) {

    }
    public void breed() {

    }

    public void walk() {
        int numberOfStepsLeft = 3; //todo add speed here
        while (numberOfStepsLeft > 0) {
            if (makeADecision()) {
                changeLocation();
                numberOfStepsLeft--;
            } else {
                break;
            }
        }
    }

    private boolean makeADecision() {
        return false;
    }

    private void changeLocation() {
    }

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }
}
