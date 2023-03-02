package com.electron3d.model.creatures.animals.predators;

import com.electron3d.model.creatures.AnimalSpecification;
import com.electron3d.model.island.Cell;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class WolfTest {
    static AnimalSpecification mockedSpecs;
    static Cell mockedCell;
    @BeforeAll
    public static void initMocks() {
        mockedCell = Mockito.mock(Cell.class);
        mockedSpecs = Mockito.mock(AnimalSpecification.class);
    }
    @Test
    public void constructor_nullSpecificationProvided_exceptionExpected() {
        assertThrows(IllegalArgumentException.class, () -> new Wolf(null, mockedCell));
    }
    @Test
    public void constructor_nullSpecificationProvided_correctExceptionMessageExpected() {
        try {
            new Wolf(null, mockedCell);
        } catch (IllegalArgumentException exception) {
            assertEquals("Specification can't be null.", exception.getMessage());
        }
    }
    @Test
    public void constructor_nullLocationProvided_exceptionExpected() {
        assertThrows(IllegalArgumentException.class, () -> new Wolf(mockedSpecs, null));
    }
    @Test
    public void constructor_nullLocationProvided_correctExceptionMessageExpected() {
        try {
            new Wolf(mockedSpecs, null);
        } catch (IllegalArgumentException exception) {
            assertEquals("Current location can't be null.", exception.getMessage());
        }
    }
}
