package Server.CDC;

import java.awt.*;

public class Player {
    public int id;
    public String name;
    public Point coordinate;
    public Point coordinateNext;

    public int deadTime = 0;
    public int usedBomb = 0;
    public boolean shouldCharacterSync = true;
    Direction direction = Direction.DOWN;

    public Player(int id, String name, Point coordinate) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.coordinateNext = coordinate;
    }
}
