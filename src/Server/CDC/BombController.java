package Server.CDC;

import java.awt.*;
import java.util.ArrayList;

class BombController {
    private GameMap gameMap;
    private ArrayList<Bomb> bombs;
    private int bombCount = 0;

    BombController(GameMap gameMap) {
        this.gameMap = gameMap;
        this.bombs = new ArrayList<>();
        BombTimer bombTimer = new BombTimer(bombs, gameMap);
        bombTimer.start();
    }

    void generate(int playerId, Point coordinate) {
        // TODO: check map to put bomb
        gameMap.getOriginalMap();
        bombs.add(new Bomb(bombCount++, playerId, coordinate, 100));
    }

    ArrayList<Bomb> getBombList() {
        return bombs;
    }
}
