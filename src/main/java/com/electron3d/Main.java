package com.electron3d;

import com.electron3d.model.island.Island;

public class Main {
    public static void main(String[] args) {
        Island island = new Island(10, 10);
        System.out.println(island);
    }
}