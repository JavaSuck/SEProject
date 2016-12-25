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
}
