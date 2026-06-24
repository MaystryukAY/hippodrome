import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void constructor_WhenHorsesIsNull_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(null)
        );
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void constructor_WhenHorsesIsEmpty_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Hippodrome(List.of())
        );
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorses_ReturnsUnmodifiableListWithSameOrder() {
        List<Horse> horses = createHorses(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        List<Horse> returnedHorses = hippodrome.getHorses();

        assertNotSame(horses, returnedHorses);
        assertEquals(horses.size(), returnedHorses.size());
        for (int i = 0; i < horses.size(); i++) {
            assertSame(horses.get(i), returnedHorses.get(i));
        }
    }

    @Test
    void move_CallsMoveOnAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(new Horse("Horse" + i, 1.0 + i));
        }

        // Запоминаем начальные дистанции
        List<Double> initialDistances = new ArrayList<>();
        for (Horse horse : horses) {
            initialDistances.add(horse.getDistance());
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        // Проверяем, что у каждой лошади дистанция увеличилась
        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            assertTrue(horse.getDistance() > initialDistances.get(i),
                    "Дистанция лошади " + horse.getName() + " должна была увеличиться");
        }
    }

    @Test
    void getWinner_ReturnsHorseWithMaxDistance() {
        Horse horse1 = new Horse("Slow", 1.0, 5.0);
        Horse horse2 = new Horse("Medium", 1.0, 10.0);
        Horse horse3 = new Horse("Fast", 1.0, 15.0);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));
        Horse winner = hippodrome.getWinner();
        assertSame(horse3, winner);
    }

    private List<Horse> createHorses(int count) {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            horses.add(new Horse("Horse" + i, i + 1.0, i * 2.0));
        }
        return horses;
    }
}