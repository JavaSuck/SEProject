package Server.CDC;

import org.json.JSONObject;
import java.util.ArrayList;

public class CDC {
    private GameMode gameMode;
    private GameState gameState;
    private GameController gameController;
    private PlayController playController;
    private BombController bombController;
    private GameMap gameMap;

    public void addVirtualCharacter(int clientNo) {

    }

    public void addBomb(int clientNo, int x, int y) {

    }

    public void updateDirection(int clientNo, int moveCode) {

    }

    public static void getUpdatingThread() {

    }

    public static ArrayList<JSONObject> getUpdatingInfo() {
        return new ArrayList<JSONObject>() {{
            JSONObject info = new JSONObject();
            info.append("Character", "Jason Wu");
            info.append("Item", "Bomb");
            add(info);
        }};
    }
}
