package Server.CDC;

import java.awt.*;

public class Bomb {
    public int playerId;
    public Point coordinate;
    public int expireTime;
    public int power = GameMode.bombPower;

    Bomb(int playerId, Point coordinate, int expireTime) {
        this.playerId = playerId;
        this.coordinate = coordinate;
        this.expireTime = expireTime;
    }
}
