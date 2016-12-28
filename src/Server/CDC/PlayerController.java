package Server.CDC;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class PlayerController {
    private GameMap gameMap;
    private ArrayList<Player> players;

    PlayerController(GameMap gameMap) {
        this.gameMap = gameMap;
        players = new ArrayList<>();
    }

//    public void recieveAction(JSONObject action) {
//
//        int playerID = 1;
//
//        String method = action.get("method").toString();
//        JSONObject params = (JSONObject) action.get("params");
//        if (method.equals("walk")) {
//            walk(playerID, (Direction) params.get("direction"));
//        } else if (method.equals("dead")) {
//            dead(playerID);
//        }
//    }

    public Point getNextCoordinate(Point coordinate, Direction direction) {
        final int OBSTACLE = 1;

        int nextBlock = getNextBlock(coordinate, direction);

        if (nextBlock == OBSTACLE)
            return coordinate;

        int x = (int)coordinate.getX();
        int y = (int)coordinate.getY();
        switch (direction) {
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            case UP:
                y--;
                break;
        }
        return new Point(x, y);
    }

    public boolean slip(int playerId, Direction direction) {

        Player player = players.get(playerId);

        //Request will invalid while player is walking.
        if (!player.shouldCharacterSync) {
            return false;
        }

        player.direction = direction;
        new Thread(() -> {
            player.shouldCharacterSync = false;
            try {
                //if player is DEAD, it would NOT continue slide.
                final int OBSTACLE = 1;

                int nextBlock = getNextBlock(player.coordinate, direction);

                while (nextBlock != OBSTACLE && player.deadTime == 0) {
                    player.coordinateNext = getNextCoordinate(player.coordinate, direction);
                    sleep(GameMode.movePeriod);
                    player.coordinate = player.coordinateNext;
//                    sleep((long)(GameMode.movePeriod * 0.3));

                    nextBlock = getNextBlock(player.coordinate, direction);
                }
                player.shouldCharacterSync = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return true;
    }

    public void dead(int playerId) {
        Player player = players.get(playerId);
        player.deadTime = GameState.gameTime;
    }

    public ArrayList<Player> getPlayerList() {
        return players;
    }

    private int getNextBlock(Point coordinate, Direction direction){
        int nextBlock = -1;
        int [][] mapData = gameMap.getOriginalMap();
        int x = (int)coordinate.getX();
        int y = (int)coordinate.getY();
        switch (direction) {
            case DOWN:
                nextBlock = mapData[y + 1][x];
                break;
            case LEFT:
                nextBlock = mapData[y][x - 1];
                break;
            case RIGHT:
                nextBlock = mapData[y][x + 1];
                break;
            case UP:
                nextBlock = mapData[y - 1][x];
        }

        return nextBlock;
    }
}
