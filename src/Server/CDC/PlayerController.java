package Server.CDC;

import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class PlayerController {
    private GameMap gameMap;
    private ArrayList<Player> players;

    PlayerController(GameMap gameMap) {
        this.gameMap = gameMap;
        players = new ArrayList<>();
    }

    void recieveAction(JSONObject action) {
        String method = action.get("method").toString();
        JSONObject params = (JSONObject) action.get("params");
        if (method.equals("walk")) {
            walk(1, (Direction) params.get("direction"));
        } else if (method.equals("dead")) {
            dead(1);
        }
    }

    public void walk(int playerId, Direction direction) {
        Player player = players.get(playerId);
        player.direction = direction;

        int x = player.coordinate.x;
        int y = player.coordinate.y;
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
        }
        player.coordinate = new Point(x, y);
    }

    public boolean slip(int playerId, Direction direction){

        Player player = players.get(playerId);

        //Request will invalid while player is walking.
        if(player.isWalk)
            return false;

        final int OBSTACLE = 1;

        new Thread(() -> {

            player.direction = direction;

            int x = player.coordinate.x;
            int y = player.coordinate.y;

            player.isWalk = true;

            //if player is DEAD, it would NOT continue slide.
            while(gameMap.getOriginalMap()[x][y] != OBSTACLE || player.deadTime!=0) {
                x = player.coordinate.x;
                y = player.coordinate.y;
                walk(playerId, direction);
            }

            player.isWalk = false;

        }).start();

        return true;
    }

    void dead(int playerId) {
        Player p = players.get(playerId);
        p.deadTime = GameState.gameTime;
    }

    public ArrayList<Player> getPlayerList() {
        return players;
    }
}
