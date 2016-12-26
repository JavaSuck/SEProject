package Server.CDC;

import org.json.JSONObject;

import java.awt.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class CDC {
    public GameController gameController;
    public PlayerController playerController;
    public BombController bombController;
    private GameMap gameMap;

    public CDC() {
        gameMap = new GameMap();
        playerController = new PlayerController(gameMap);
        bombController = new BombController(gameMap);
    }

    public void addVirtualCharacter(int playerId, InetAddress address) {
        playerController.getPlayerList().add(new Player(playerId, "YPC", new Point(0, 0)));
    }

    public void removeVirtualCharacter(int playerId) {
        playerController.getPlayerList().remove(playerId);
    }

    public void addBomb(int playerId) {
        Point playerCoordinate = playerController.getPlayerList().get(playerId).coordinate;
        bombController.generate(playerId, playerCoordinate);
    }

    public void updateDirection(int playerId, Direction direction) {
        playerController.getPlayerList().get(playerId).direction = direction;
    }

    public void getUpdatingThread() {

    }

    public ArrayList<JSONObject> getUpdatingInfo() {
        return new ArrayList<JSONObject>() {{
            JSONObject info = new JSONObject();
            info.append("Character", "Jason Wu");
            info.append("Item", "Bomb");
            add(info);
        }};
    }
}
