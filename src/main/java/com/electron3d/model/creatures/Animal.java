package com.electron3d.model.creatures;

import com.electron3d.model.island.Field;

public abstract class Animal {
    private Field location;

    public Animal(Field location) {
        this.location = location;
    }
    public void eat(Eatable food) {

    }
    public void breed() {

    }
    public void walk() {

    }

    public Field getLocation() {
        return location;
    }

    public void setLocation(Field location) {
        this.location = location;
    }
}
