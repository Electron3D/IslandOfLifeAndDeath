package com.electron3d.model.island;

import com.electron3d.model.Animal;

import java.util.List;

public class Field {
    int x;
    int y;
    List<Animal> animals;
    int plants;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }
}
