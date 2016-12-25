package Server.CDC;

import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class BombTimer extends Thread {

    private GameMap gameMap;
    private ArrayList<Bomb> bombs;

    BombTimer(ArrayList<Bomb> bombs, GameMap gameMap) {
        this.bombs = bombs;
        this.gameMap = gameMap;
    }

    public void run() {
        try {
            startTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTimer() throws InterruptedException {
        while (true) {
            for (Bomb bomb : bombs) {
                if (bomb.expireTime == GameState.gameTime) {
                    explode(bomb);
                }
            }
            sleep(20);
        }
    }

    private void explode(Bomb bomb) {
        // TODO: effect map content
        gameMap.getOriginalMap();
        bombs.remove(bomb);
    }
}
