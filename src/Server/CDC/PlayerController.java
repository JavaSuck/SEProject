package Server.CDC;

import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PlayerController {
  private GameMap gameMap;
  private ArrayList<Player> players;

  PlayerController(GameMap gameMap) {
    this.gameMap = gameMap;
    players = new ArrayList<>();
  }

  public void recieveAction(JSONObject action) {

    int playerID = 1;

    String method = action.get("method").toString();
    JSONObject params = (JSONObject) action.get("params");
    if (method.equals("walk")) {
      walk(playerID, (Direction) params.get("direction"));
    } else if (method.equals("dead")) {
      dead(playerID);
    }
  }

  public void walk(int playerId, Direction direction) {

    final int OBSTACLE = 1;

    Player player = players.get(playerId);
    //player.direction = direction;

    int x = player.coordinate.x;
    int y = player.coordinate.y;

    int nextDestination = getNextBlock(x ,y, direction);

    if(nextDestination == OBSTACLE)
      return;

    switch (direction) {
      case DOWN:
        y++;
        break;
      case LEFT:
        x--;
        break;
      case RIGHT:
        x++;
        break;
      case UP:
        y--;
        break;
    }
    player.coordinate = new Point(x, y);
  }

  public boolean slip(int playerId, Direction direction) {

    Player player = players.get(playerId);
    player.direction = direction;

    //Request will invalid while player is walking.
    if (player.isWalk)
      return false;

    new Thread(() -> {
      int x = player.coordinate.x;
      int y = player.coordinate.y;

      player.isWalk = true;
      try {
        //if player is DEAD, it would NOT continue slide.
        final int OBSTACLE = 1;

        int nextDestination = getNextBlock(x, y, direction);

        // first time delay
        if (nextDestination != OBSTACLE && player.deadTime == 0) {
          sleep(GameMode.movePeriod / 2);
        }

        while (nextDestination != OBSTACLE && player.deadTime == 0) {
          walk(playerId, direction);
          sleep(GameMode.movePeriod);

          x = player.coordinate.x;
          y = player.coordinate.y;

          nextDestination = getNextBlock(x, y, direction);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      player.isWalk = false;
    }).start();

    return true;
  }

  public void dead(int playerId) {
    Player p = players.get(playerId);
    p.deadTime = GameState.gameTime;
  }

  public ArrayList<Player> getPlayerList() {
    return players;
  }


  private int getNextBlock(int x, int y, Direction direction){
    int nextDestination = -1;
    int [][] mapData = gameMap.getOriginalMap();
    switch (direction) {
      case DOWN:
        nextDestination = mapData[y+1][x];
        break;
      case LEFT:
        nextDestination = mapData[y][x-1];
        break;
      case RIGHT:
        nextDestination = mapData[y][x+1];
        break;
      case UP:
        nextDestination = mapData[y-1][x];
    }

    return nextDestination;
  }
}
