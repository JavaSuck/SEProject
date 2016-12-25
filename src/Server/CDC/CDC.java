package Server.CDC;

import org.json.JSONObject;

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



    public void addVirtualCharacter(int playerID, InetAddress addr) {

    }

    public void removeVirtualCharacter(int playerID){

    }

    public void addBomb(int playerID) {

    }

    public void updateDirection(int playerID, int moveCode) {

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
