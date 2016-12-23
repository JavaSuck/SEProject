package Server.CDC;

import java.awt.*;

public class Player {
  public int playerId;
  public String name;
  public Point coordinate;
  public int deadTime = 0;
  public int usedBomb = 0;
  public boolean isWalking = false;
  public int direction = 2; // 上 0, 右 1, 下 2, 左 3
}
