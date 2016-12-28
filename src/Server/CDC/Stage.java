package Server.CDC;

public enum Stage {
    LOGIN(0),
    LOADING(1),
    GAME(2),
    RESULT(3);
    private int value;
    private static Stage[] allValues = values();

    Stage(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Stage getStage(int n) {
        return allValues[n];
    }
}
