package Server.CDC;

import java.awt.*;

class Bomb {
    public int playerId;
    public Point coordinate;
    public int expireTime;
    public int power;

    Bomb(int playerId, Point coordinate, int expireTime, int power) {
        this.playerId = playerId;
        this.coordinate = coordinate;
        this.expireTime = expireTime;
        this.power = power;
    }
}
