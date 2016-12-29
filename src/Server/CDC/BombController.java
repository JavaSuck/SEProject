package Server.CDC;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

@SuppressWarnings("InfiniteLoopStatement")
class BombController {
    private GameMap gameMap;
    private ArrayList<Player> players;
    private ArrayList<Bomb> bombs;
    private int bombCount = 0;

    BombController(GameMap gameMap, PlayerController playerController) {
        this.gameMap = gameMap;
        this.players = playerController.getPlayerList();
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
        int[][] mapData = gameMap.getOriginalMap();
        int bombX = (int) bomb.coordinate.getX();
        int bombY = (int) bomb.coordinate.getY();
        int effectBlock = bomb.power;
        ArrayList<Point> effectPoints = new ArrayList<>();
        ArrayList<Point> effectObstacles = new ArrayList<>();
        mapData[bombY][bombX] = 0;
        bomb.isExist = false;
        System.out.println("Bomb" + bomb.id + " explode!");
        // Check if out of map range and stop at obstacle
        --bomb.explosionRange[0];
        --bomb.explosionRange[2];

        int completeCount=0;

        Thread[] sideThread = new Thread[4];

        sideThread[2] = new Thread(()->{
            for (int effectX = bombX; effectX <= bombX + effectBlock; effectX++) {
                if (effectX >= 0 && effectX < 17 && mapData[bombY][effectX] != 1) {
                    effectPoints.add(new Point(effectX, bombY));
                    ++bomb.explosionRange[2];
                } else if (effectX >= 0 && effectX < 17 && mapData[bombY][effectX] == 1) {
                    effectObstacles.add(new Point(effectX, bombY));
                    break;
                } else if (effectX < 0 || effectX >= 17 || mapData[bombY][effectX] == 1) {
                    break;
                }
            }
        });

        sideThread[1] = new Thread(()->{
        for (int effectX = bombX - 1; effectX >= bombX - effectBlock; effectX--) {
            if (effectX >= 0 && effectX < 17 && mapData[bombY][effectX] != 1) {
                effectPoints.add(new Point(effectX, bombY));
                ++bomb.explosionRange[1];
            } else if (effectX >= 0 && effectX < 17 && mapData[bombY][effectX] == 1) {
                effectObstacles.add(new Point(effectX, bombY));
                break;
            } else if (effectX < 0 || effectX >= 17 || mapData[bombY][effectX] == 1) {
                break;
            }
        }});

        sideThread[0] = new Thread(()->{
        for (int effectY = bombY; effectY <= bombY + effectBlock; effectY++) {
            if (effectY >= 0 && effectY < 17 && mapData[effectY][bombX] != 1) {
                effectPoints.add(new Point(bombX, effectY));
                ++bomb.explosionRange[0];
            } else if (effectY >= 0 && effectY < 17 && mapData[effectY][bombX] == 1) {
                effectObstacles.add(new Point(bombX, effectY));
                break;
            } else if (effectY < 0 || effectY >= 17 || mapData[effectY][bombX] == 1) {
                break;
            }
        }});

        sideThread[3] = new Thread(()->{
        for (int effectY = bombY - 1; effectY >= bombY - effectBlock; effectY--) {
            if (effectY >= 0 && effectY < 17 && mapData[effectY][bombX] != 1) {
                effectPoints.add(new Point(bombX, effectY));
                ++bomb.explosionRange[3];
            } else if (effectY >= 0 && effectY < 17 && mapData[effectY][bombX] == 1) {
                effectObstacles.add(new Point(bombX, effectY));
                break;
            } else if (effectY < 0 || effectY >= 17 || mapData[effectY][bombX] == 1) {
                break;
            }
        }});

        for(int i=0; i<sideThread.length; i++)
            sideThread[i].start();

        while(sideThread[0].isAlive() | sideThread[1].isAlive() | sideThread[2].isAlive() | sideThread[3].isAlive());

        checkChainBomb(effectObstacles);
        checkPlayerDead(effectPoints);
//        new Thread(() -> {
//            try {
//                sleep(10000);
//                bombs.remove(bomb);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    private void checkPlayerDead(ArrayList<Point> effectPoints) {
//        updateObjectState(effectPoints);
        new Thread(() -> {
            try {
                int endExplosionTime = GameState.gameTime + GameMode.bombExplosionDuration;
                while (GameState.gameTime <= endExplosionTime) {
                    updateObjectState(effectPoints);
                    sleep(50);
                }
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    private void updateObjectState(ArrayList<Point> effectPoints) {
        for (Point point : effectPoints) {
            int x = (int) point.getX();
            int y = (int) point.getY();
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
    }

    private void checkChainBomb(ArrayList<Point> effectObstacles) {

        int listLength = effectObstacles.size();
        Point[] point = effectObstacles.toArray(new Point[listLength]);


        //The smaller the value of jobLength, the higher the parallelism.
        int jobLength = 10;

        int taskThreadNumber = listLength/jobLength;
        if(listLength % jobLength == 0)
            taskThreadNumber++;

        Thread[] taskThread = new Thread[taskThreadNumber];

        int taskIndex = 0;
        while(taskIndex < taskThread.length -1) {

            final int jobStartIndex = taskIndex * jobLength;
            final int jobEndIndex;

            if(jobStartIndex + jobLength -1 > listLength) {
                jobEndIndex = listLength - 1;
            }
            else {
                jobEndIndex = jobStartIndex + jobStartIndex + jobLength - 1;
            }

            taskThread[taskIndex] = new Thread(() -> {

                for (int i = jobStartIndex; i <= jobEndIndex; i++) {
                    int x = (int) point[i].getX();
                    int y = (int) point[i].getY();
                    for (Bomb bomb : bombs) {
                        int bombX = (int) bomb.coordinate.getX();
                        int bombY = (int) bomb.coordinate.getY();
                        if (bomb.isExist && bombX == x && bombY == y) {
                            bomb.expireTime = GameState.gameTime;
                        }
                    }
                }
            });

            taskThread[taskIndex].start();
            taskIndex++;
        }

        for(Thread currentThread: taskThread){
            while(currentThread.isAlive());
        }



    }

    ArrayList<Bomb> getBombList() {
        return bombs;
    }
}
