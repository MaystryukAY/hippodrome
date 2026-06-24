import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorseTest {

    private static final String TEST_NAME = "TestHorse";
    private static final double TEST_SPEED = 2.5;
    private static final double TEST_DISTANCE = 10.0;

    @Test
    void constructor_WhenNameIsNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(null, TEST_SPEED, TEST_DISTANCE)
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n", "   "})
    void constructor_WhenNameIsBlank_ThrowsIllegalArgumentException(String blankName) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(blankName, TEST_SPEED, TEST_DISTANCE)
        );
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void constructor_WhenSpeedIsNegative_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(TEST_NAME, -1.0, TEST_DISTANCE)
        );
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void constructor_WhenDistanceIsNegative_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Horse(TEST_NAME, TEST_SPEED, -1.0)
        );
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getName_ReturnsCorrectName() {
        Horse horse = new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE);
        assertEquals(TEST_NAME, horse.getName());
    }

    @Test
    void getSpeed_ReturnsCorrectSpeed() {
        Horse horse = new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE);
        assertEquals(TEST_SPEED, horse.getSpeed());
    }

    @Test
    void getDistance_ReturnsCorrectDistance() {
        Horse horse = new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE);
        assertEquals(TEST_DISTANCE, horse.getDistance());
    }

    @Test
    void getDistance_WhenTwoArgConstructor_ReturnsZero() {
        Horse horse = new Horse(TEST_NAME, TEST_SPEED);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void move_CallsGetRandomDoubleWithCorrectParams() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE);

            horse.move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.5, 0.9})
    void move_CalculatesDistanceCorrectly(double randomValue) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse(TEST_NAME, TEST_SPEED, TEST_DISTANCE);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);

            double expectedDistance = TEST_DISTANCE + TEST_SPEED * randomValue;
            horse.move();

            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}