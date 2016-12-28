package Server.CDC;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class CDC {
    public GameController gameController;
    private PlayerController playerController;
    private BombController bombController;
    private GameMap gameMap;

    public CDC() {
        gameMap = new GameMap();
        playerController = new PlayerController(gameMap);
        bombController = new BombController(gameMap, playerController);
        gameController = new GameController(playerController);
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
        Player player = playerController.getPlayerList().get(playerId);

        //if player is dead
        if(player.deadTime!=0) {
          return false;
        }

        // Check if player can put bomb
        int placedBombs = 0;

        ArrayList<Bomb> bombs =  bombController.getBombList();
        for (Bomb bomb : bombs) {
            if (bomb.isExist && bomb.playerId == playerId)
                ++placedBombs;
        }
        if (placedBombs >= GameMode.playerMaxBomb) {
            System.out.println("The number of bombs has been MAX");
            return false;
        }

        // Check if there are no bombs
        Point playerCoordinate = player.coordinate;
        int[][] mapData = gameMap.getOriginalMap();
        int bombX = (int) playerCoordinate.getX();
        int bombY = (int) playerCoordinate.getY();
        if (mapData[bombY][bombX] == 1) {
            System.out.println("A bomb already exists here");
            return false;
        }

        bombController.generate(playerId, playerCoordinate);
        return true;
    }

    public boolean playerSlip(int playerId, Direction direction) {
        return playerController.slip(playerId, direction);
    }

    public void getUpdatingThread() {

    }

    public JSONObject getUpdatingInfo() {
        return new JSONObject() {{
            JSONArray players = new JSONArray();
            for (Player player : playerController.getPlayerList()) {
                JSONObject playerObject = new JSONObject();
                playerObject.put("playerId", player.id);
                playerObject.put("coordinateNextX", player.coordinateNext.getX());
                playerObject.put("coordinateNextY", player.coordinateNext.getY());
                playerObject.put("deadTime", player.deadTime);
                playerObject.put("usedBomb", player.usedBomb);
                playerObject.put("shouldCharacterSync", player.shouldCharacterSync);
                playerObject.put("direction", player.direction.getValue());
                players.put(playerObject);
            }
            put("players", players);
            JSONArray bombs = new JSONArray();
            for (Bomb bomb : bombController.getBombList()) {
                JSONObject bombObject = new JSONObject();
                bombObject.put("bombId", bomb.id);
                bombObject.put("playerId", bomb.playerId);
                bombObject.put("coordinateX", bomb.coordinate.getX());
                bombObject.put("coordinateY", bomb.coordinate.getY());
                bombObject.put("expireTime", bomb.expireTime);
                bombObject.put("isExist", bomb.isExist);
                bombObject.put("power", bomb.power);
                bombObject.put("explosionRange", bomb.explosionRange);
                bombs.put(bombObject);
            }
            put("bombs", bombs);
            JSONObject gameState = new JSONObject();
            gameState.put("gameTime", GameState.gameTime);
            gameState.put("livedPlayer", GameState.livedPlayer);
            gameState.put("stage", GameState.stage.getValue());
            put("gameState", gameState);
        }};
    }

    private Point[] getStartLocation() {
        final int startLocationBlock = 2;

        Point[] startLocation = new Point[GameMode.playerCount];
        int[][] map = gameMap.getOriginalMap();
        int startLocationIndex = 0;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
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
