package Server.CDC;

import java.awt.*;
import java.util.ArrayList;

class BombController {
    private GameMap gameMap;
    private ArrayList<Bomb> bombs;

    BombController(GameMap gameMap) {
        this.gameMap = gameMap;
        this.bombs = new ArrayList<>();
        BombTimer bombTimer = new BombTimer(bombs, gameMap);
        bombTimer.start();
    }

    private Boolean generate(int playerId, Point coordinate) {
        // TODO: check map to put bomb & return result
        gameMap.getOriginalMap();
        bombs.add(new Bomb(playerId, coordinate, 100, 3));
        return true;
    }

    public ArrayList<Bomb> getBombList() {
        return bombs;
    }
}
