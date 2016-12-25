package Server.CDC;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerController {
    private GameMap gameMap;
    //  private List players;
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

    void recieveAction(HashMap action) {
        String function = (String) action.get("function");
        if (function.equals("walk")) {
            walk(1, (Direction) action.get("direction"));
        } else if (function.equals("dead")) {
            dead(1);
        }
    }

    public void walk(int id, Direction direction) {
        Player player = players.get(id);
        player.direction = direction;

        int x = player.coordinate.x;
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

    void dead(int id) {
        Player p = players.get(id);
        p.deadTime = GameState.gameTime;
    }

    public ArrayList<Player> getPlayerList() {
        return players;
    }
}
