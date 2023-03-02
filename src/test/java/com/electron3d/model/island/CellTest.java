package com.electron3d.model.island;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellTest {
    @Test
    public void constructor_negativeXProvided_exceptionExpected() {
        assertThrows(IllegalArgumentException.class, () -> new Cell(-1, 0));
    }
    @Test
    public void constructor_negativeXProvided_correctExceptionMessageExpected() {
        try {
            new Cell(-1, 0);
        } catch (IllegalArgumentException exception) {
            assertEquals("Variable X can't be negative.", exception.getMessage());
        }
    }
    @Test
    public void constructor_negativeYProvided_exceptionExpected() {
        assertThrows(IllegalArgumentException.class, () -> new Cell(0, -1));
    }
    @Test
    public void constructor_negativeYProvided_correctExceptionMessageExpected() {
        try {
            new Cell(0, -1);
        } catch (IllegalArgumentException exception) {
            assertEquals("Variable Y can't be negative.", exception.getMessage());
        }
    }
    @Test
    public void getters_correctParamsProvided_sameVariablesExpected() {
        int x = 1;
        int y = 1;
        Cell cell = new Cell(x, y);
        assertEquals(x, cell.getX());
        assertEquals(y, cell.getY());
    }
}
