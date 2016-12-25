package Server.CDC;

import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class PlayerController {
  private GameMap gameMap;
  public ArrayList<Player> players;

  public PlayerController(GameMap gameMap) {
    this.gameMap = gameMap;
    Player player1 = new Player();
    player1.id = 1;
    player1.name = "YPA";
    player1.coordinate = new Point(0, 0);

    Player player2 = new Player();
    player2.id = 1;
    player2.name = "YPB";
    player2.coordinate = new Point(1, 0);

    Player player3 = new Player();
    player3.id = 1;
    player3.name = "YPC";
    player3.coordinate = new Point(0, 1);


    Player player4 = new Player();
    player4.id = 1;
    player4.name = "YPD";
    player4.coordinate = new Point(0, 1);

    players = new ArrayList<>();

    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
  }

  void recieveAction(JSONObject action) {
    String method = (String) action.get("method");
    JSONObject params = (JSONObject) action.get("params");
    if (method == "walk") {
      walk(1, (Direction) params.get("direction"));
    } else if (method == "dead") {
      dead(1);
    }
  }

  public void walk(int playerId, Direction direction) {
    Player player = players.get(playerId);
    player.direction = direction;


    int   x = player.coordinate.x;
    int y = player.coordinate.y;
    switch (direction) {
      case DOWN:
        y++;
      case LEFT:
        x--;
      case RIGHT:
        x++;
      case UP:
        y--;
    }
    player.coordinate = new Point(x, y);
  }

  void dead(int playerId) {
    Player p = players.get(playerId);
    p.deadTime = GameState.gameTime;
  }

  public ArrayList<Player> getPlayerList() {
    return players;
  }
}
