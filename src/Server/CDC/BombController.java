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
                        if (bomb.isExist && GameState.gameTime >= bomb.expireTime) {
                            explode(bomb);
                            break;
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
        int effectBlock = (bomb.power - 1) / 2;
        mapData[bombY][bombX] = 0;
        bomb.isExist = false;
        System.out.println("Bomb" + bomb.id + " explode!");
        // TODO: Chain explode bomb
        // Check if out of map range and stop at obstacle
        --bomb.explosionRange[0];
        --bomb.explosionRange[2];
        for (int effectX = bombX; effectX <= bombX + effectBlock; effectX++) {
            if (effectX >= 0 && effectX < 17 && mapData[bombY][effectX] != 1) {
                checkPlayerDead(effectX, bombY);
                ++bomb.explosionRange[2];
            } else if (effectX < 0 || effectX >= 17 || mapData[bombY][effectX] == 1) {
                break;
            }
        }
        for (int effectX = bombX - 1; effectX >= bombX - effectBlock; effectX--) {
            if (effectX >= 0 && effectX < 17 && mapData[bombY][effectX] != 1) {
                checkPlayerDead(effectX, bombY);
                ++bomb.explosionRange[1];
            } else if (effectX < 0 || effectX >= 17 || mapData[bombY][effectX] == 1) {
                break;
            }
        }
        for (int effectY = bombY; effectY <= bombY + effectBlock; effectY++) {
            if (effectY >= 0 && effectY < 17 && mapData[effectY][bombX] != 1) {
                checkPlayerDead(bombX, effectY);
                ++bomb.explosionRange[0];
            } else if (effectY < 0 || effectY >= 17 || mapData[effectY][bombX] == 1) {
                break;
            }
        }
        for (int effectY = bombY - 1; effectY >= bombY - effectBlock; effectY--) {
            if (effectY >= 0 && effectY < 17 && mapData[effectY][bombX] != 1) {
                checkPlayerDead(bombX, effectY);
                ++bomb.explosionRange[3];
            } else if (effectY < 0 || effectY >= 17 || mapData[effectY][bombX] == 1) {
                break;
            }
        }
        /*new Thread(() -> {
            try {
                sleep(10000);
                bombs.remove(bomb);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    private void checkPlayerDead(int x, int y) {
        ArrayList<Player> players = playerController.getPlayerList();
        for (Player player : players) {
            int playerX = (int) player.coordinate.getX();
            int playerY = (int) player.coordinate.getY();
            if (playerX == x && playerY == y && player.deadTime == 0) {
                player.deadTime = GameState.gameTime;
                player.shouldCharacterSync = true;
                System.out.println("Player" + player.id + " die!");
            }
        }
    }

    ArrayList<Bomb> getBombList() {
        return bombs;
    }
}
