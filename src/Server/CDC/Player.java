package Server.CDC;

import java.awt.*;

public class Player {
    public int id;
    public String name;
    public Point coordinate;
    public int deadTime = 0;
    public int usedBomb = 0;
    public boolean isWalking = false;
    Direction direction = Direction.DOWN;

    public Player(int id, String name, Point coordinate) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
    }
}
