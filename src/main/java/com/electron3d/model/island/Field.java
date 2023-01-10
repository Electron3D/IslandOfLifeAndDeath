package com.electron3d.model.island;

import java.util.HashMap;
import java.util.Map;

public class Field {
    private final int x;

    private final int y;

    public final Map<String, Integer> amountOfAnimalsOnTheField;

    private int plantsOnThisField;

    public Field(int x, int y) {
        this.x = x;
        this.y = y;
        this.amountOfAnimalsOnTheField = new HashMap<>();
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPlantsOnThisField() {
        return plantsOnThisField;
    }

    public void setPlantsOnThisField(int plantsOnThisField) {
        this.plantsOnThisField = plantsOnThisField;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "|plants:" + plantsOnThisField + "}";
    }
}
