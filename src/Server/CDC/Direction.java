package Server.CDC;

public enum Direction {
    DOWN(0),
    LEFT(1),
    RIGHT(2),
    UP(3);

    private int value;
    private static Direction[] allValues = values();

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Direction getDirection(int n) {
        return allValues[n];
    }
}
