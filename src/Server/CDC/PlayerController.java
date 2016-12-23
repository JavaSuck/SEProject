package Server.CDC;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PlayerController {
  private GameMap gameMap;
//  private List players;
  public HashMap players;

  public PlayerController(GameMap gameMap)  {
    this.gameMap = gameMap;
    Player player1 = new Player();
    player1.playerId = 1;
    player1.name = "YPA";
    player1.coordinate = new Point(0, 0);

    Player player2 = new Player();
    player2.playerId = 1;
    player2.name = "YPB";
    player2.coordinate = new Point(1, 0);

    Player player3 = new Player();
    player3.playerId = 1;
    player3.name = "YPC";
    player3.coordinate = new Point(0, 1);

    Player player4= new Player();
    player4.playerId = 1;
    player4.name = "YPC";
    player4.coordinate = new Point(0, 1);

    players = new HashMap();

    players.put(1, player1);
    players.put(2, player2);
    players.put(3, player3);
    players.put(4, player4);
  }

  void recieveAction(HashMap action) {
    String func = (String)action.get("function");
    if (func == "walk") {
      walk(1, (int)action.get("direction"));
    } else if (func == "dead") {
      dead(1);
    }
  }

  void walk(int playerId, int direction) {
    Player p = (Player)players.get(playerId);
    p.direction = direction;


    int x = p.coordinate.x;
    int y = p.coordinate.y;
    if (direction == 0) {
      y--;
    } else if (direction == 1) {
      x++;
    } else if (direction == 2) {
      y++;
    } else if (direction == 3) {
      x--;
    }
    p.coordinate = new Point(x, y);
  }

  void dead(int playerId) {
    Player p = (Player)players.get(playerId);
    p.deadTime = GameState.gameTime;
  }
}
