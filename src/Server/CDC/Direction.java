package Server.CDC;

public enum Direction {
    DOWN(0),
    LEFT(1),
    RIGHT(2),
    UP(3);

    private int value;

    Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
