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

    public boolean addVirtualCharacter(int playerId, InetAddress address) {
        Point[] startLocation = getStartLocation();
        playerController.getPlayerList().add(new Player(playerId, "YPC", startLocation[playerId]));
        return true;
    }

    public boolean removeVirtualCharacter(int playerId) {
        playerController.getPlayerList().remove(playerId);
        return true;
    }

    public boolean addBomb(int playerId) {
        Point playerCoordinate = playerController.getPlayerList().get(playerId).coordinate;
        bombController.generate(playerId, playerCoordinate);
        return true;
    }

    public boolean updateDirection(int playerId, Direction direction) {
        // TODO: check if can walk to this side and if is Player.isCharacterSync
        playerController.getPlayerList().get(playerId).direction = direction;
        return playerController.slip(playerId, direction);
    }

    public void getUpdatingThread() {

    }

    public ArrayList<JSONObject> getUpdatingInfo() {
        return new ArrayList<JSONObject>() {{
            for (Player player : playerController.getPlayerList()) {
                JSONObject info = new JSONObject();
                info.append("playerId", player.id);
                info.append("nextCoordinateX", player.coordinate.getX());
                info.append("nextCoordinateY", player.coordinate.getY());
                info.append("deadTime", player.deadTime);
                info.append("usedBomb", player.usedBomb);
                info.append("isCharacterSync", player.isCharacterSync);
                info.append("direction", player.direction.getValue());
                add(info);
            }
        }};
    }

    private Point[] getStartLocation(){

        final int startLocationBlock = 2;

        Point[] startLocation = new Point[GameMode.playerCount];
        int[][] map = gameMap.getOriginalMap();
        int startLocationIndex = 0;

        for(int y =0; y<map.length; y++){
            for(int x=0; x<map[y].length; x++) {
                if (map[y][x] == startLocationBlock) {
                    startLocation[startLocationIndex] = new Point(x, y);
                    startLocationIndex++;
                }
            }
        }

        //Assure the GameMode and GameMap is synchronize.
        assert startLocationIndex == GameMode.playerCount;

        return startLocation;


    }
}
