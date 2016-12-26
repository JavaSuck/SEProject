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
        // TODO: check if can walk to this side and if is Player.isWalk
//        playerController.getPlayerList().get(playerId).direction = direction;
        
        playerController.slip(playerId, direction);
    }

    public void getUpdatingThread() {

    }

    public ArrayList<JSONObject> getUpdatingInfo() {
        return new ArrayList<JSONObject>() {{
            for (Player player : playerController.getPlayerList()) {
                JSONObject info = new JSONObject();
                info.append("playerId", player.id);
                info.append("coordinateX", player.coordinate.getX());
                info.append("coordinateY", player.coordinate.getY());
                info.append("deadTime", player.deadTime);
                info.append("usedBomb", player.usedBomb);
                info.append("isWalk", player.isWalk);
                info.append("direction", player.direction.getValue());
                add(info);
            }
        }};
    }
}
