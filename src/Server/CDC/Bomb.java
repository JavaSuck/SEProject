package Server.CDC;

import java.awt.*;

public class Bomb {
    public int id;
    public int playerId;
    public Point coordinate;
    public int expireTime;
    public boolean isExist;
    public int power = GameMode.bombPower;
    public int[] explosionRange = new int[4];

    Bomb(int id, int playerId, Point coordinate, int expireTime) {
        this.id = id;
        this.playerId = playerId;
        this.coordinate = coordinate;
        this.expireTime = expireTime;
        this.isExist = true;
        for (int i = 0; i < 4; i++)
            this.explosionRange[i] = 0;
    }
}
