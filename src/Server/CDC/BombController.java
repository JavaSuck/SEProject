package Server.CDC;

import java.awt.*;
import java.util.ArrayList;

class BombController {
    public GameMap gameMap;
    public ArrayList<Bomb> bombs;

    BombController(GameMap gameMap) {
        this.gameMap = gameMap;
        this.bombs = new ArrayList<>();
    }

    private void timer() {

    }

    private Boolean generate(int playerId, Point coordinate) {
        bombs.add(new Bomb(playerId, coordinate, 100, 3));
        return true;
    }

    private void explode(Bomb bomb) {

    }
}
