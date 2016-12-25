package Server.CDC;

import org.json.JSONObject;

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

    public void addVirtualCharacter(int clientNo) {

    }

    public void addBomb(int clientNo, int x, int y) {

    }

    public void updateDirection(int clientNo, Direction direction) {

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
