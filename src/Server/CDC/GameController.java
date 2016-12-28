package Server.CDC;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

@SuppressWarnings("InfiniteLoopStatement")
class GameController {

    private PlayerController playerController;

    GameController(PlayerController playerController) {
        this.playerController = playerController;
        updateGameState();
    }

    private void updateGameState() {
        new Thread(() -> {
            try {
                ArrayList<Player> players = playerController.getPlayerList();
                while (true) {
                    int livePlayer = 4;
                    for (Player player : players) {
                        if (player.deadTime != 0)
                            --livePlayer;
                    }
                    GameState.livedPlayer = livePlayer;
                    GameState.gameTime = (int) new Date().getTime();
                    sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    void playerDead() {

    }

    void nextStage() {

    }
}
