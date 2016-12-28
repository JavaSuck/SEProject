package Server.CDC;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

@SuppressWarnings("InfiniteLoopStatement")
class BombController {
    private GameMap gameMap;
    private PlayerController playerController;
    private ArrayList<Bomb> bombs;
    private int bombCount = 0;

    BombController(GameMap gameMap, PlayerController playerController) {
        this.gameMap = gameMap;
        this.playerController = playerController;
        this.bombs = new ArrayList<>();
        new Thread(() -> {
            try {
                while (true) {
                    for (Bomb bomb : bombs) {
                        if (bomb.expireTime == GameState.gameTime) {
                            explode(bomb);
                        }
                    }
                    sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void generate(int playerId, Point coordinate) {
        int[][] mapData = gameMap.getOriginalMap();
        int bombX = (int) coordinate.getX();
        int bombY = (int) coordinate.getY();
        mapData[bombY][bombX] = 1;
        bombs.add(new Bomb(bombCount++, playerId, coordinate, GameState.gameTime + GameMode.bombExpireTime));
    }

    private void explode(Bomb bomb) {
        int [][] mapData = gameMap.getOriginalMap();
        int bombX = (int) bomb.coordinate.getX();
        int bombY = (int) bomb.coordinate.getY();
        int effectBlock = (GameMode.bombPower - 1) / 2;
        mapData[bombY][bombX] = 0;
        for (int effectX = bombX - effectBlock; effectX <= bombX + effectBlock; effectX++) {
            if (effectX >= 0 && effectX <= 17 && mapData[bombY][effectX] != 1) {
                checkPlayerDead(effectX, bombY);
            }
        }
        for (int effectY = bombY - effectBlock; effectY <= bombY + effectBlock; effectY++) {
            if (effectY >= 0 && effectY <= 17 && mapData[effectY][bombX] != 1) {
                checkPlayerDead(bombX, effectY);
            }
        }
        bombs.remove(bomb);
    }

    private void checkPlayerDead(int x, int y) {
        ArrayList<Player> players = playerController.getPlayerList();
        for (Player player : players) {
            int playerX = (int) player.coordinate.getX();
            int playerY = (int) player.coordinate.getY();
            if (playerX == x && playerY == y) {
                player.deadTime = GameState.gameTime;
            }
        }
    }

    ArrayList<Bomb> getBombList() {
        return bombs;
    }
}
