package Client.DOM;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.Bomb.Explosion;
import Client.Objects.Item;
import Client.Scene.Game;
import Client.TCPClient.TCPClient;
import Client.Bomb.Bomb;
import Client.UIComponents.Sidebar;
import Server.CDC.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DOM {
    private TCPClient tcp;
    private HashMap<Integer, Item> dynamicObjects = new HashMap<>();

    private ConcurrentHashMap<Integer, VirtualCharacter> characters = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Bomb> bombs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Explosion> explosions = new ConcurrentHashMap<>();
    private BackgroundCanvas backgroundCanvas;
    private Sidebar sidebar;

    private int localPlayerId;
    public VirtualCharacter localPlayer;

    private Thread[] creatBombThread = new Thread[4];

    public DOM(TCPClient tcp, BackgroundCanvas backgroundCanvas) {
        this.tcp = tcp;
        this.backgroundCanvas = backgroundCanvas;
        this.localPlayerId = tcp.playerId;

        for (int i = 0; i < 4; i++) {
            VirtualCharacter character = new VirtualCharacter("player" + i + ".png");

            characters.put(i, character);
            if (i == localPlayerId) {
                localPlayer = character;
            } else {
                backgroundCanvas.add(character);
            }
        }
    }

    public void updateAllAnimationFrame() {
//        for (Map.Entry<Integer, VirtualCharacter> character : characters.entrySet()) {
//            character.getValue().updateAnimation();
//        }
//        for (Map.Entry<Integer, Bomb> bomb : bombs.entrySet()) {
//            bomb.getValue().updateAnimation();
//        }
//
        int[] removeList = new int[100];
        int count = 0;
        for (Map.Entry<Integer, Explosion> explosion : explosions.entrySet()) {
//            explosion.getValue().updateAnimation();
            if (explosion.getValue().getCurrentFrame() == explosion.getValue().getTotalFrames() - 1) {
                removeList[count++] = explosion.getKey();
//                explosions.remove(explosion.getKey());
                backgroundCanvas.remove(explosion.getValue());
            }
        }
        for (int i = 0; i < count; i++) {
            explosions.remove(removeList[i]);
        }
    }

    public Point getVirtualCharacterXY() {
        return characters.get(localPlayerId).getPosition();
    }

    public void updateVirtualCharacter(int playerId, Direction dir, Point coordinateNext, boolean shouldCharacterSync, int deadTime) {

        if (deadTime != 0) {
            characters.get(playerId).dead();
            sidebar.updateAvatarBox(playerId);
        } else {
            characters.get(playerId).updateCharacter(dir, coordinateNext, shouldCharacterSync);
        }
    }


    public void updateBomb(int index, int x, int y, boolean isExist, int[] explosionRange, int power) {

        // Create exist bomb
        Bomb receiveBomb = bombs.get(index);

        if (receiveBomb == null && isExist) {
            creatBombThread[index % 4] = new Thread(() -> {
                Bomb newBomb = new Bomb(index);
                newBomb.setLocation(x * Game.BLOCK_PIXEL, y * Game.BLOCK_PIXEL);
                if (bombs.get(index) == null) {
                    bombs.put(index, newBomb);
                    backgroundCanvas.add(newBomb);
                }
            });
            creatBombThread[index % 4].start();
        }

        // Exist, do explode
        else if (receiveBomb != null && !isExist) {
            new Thread(() -> {
                Bomb bomb = receiveBomb;
                bomb.stop();
                backgroundCanvas.remove(bomb);
                bombs.remove(bomb.getId());
                if (explosions.get(index) == null) {
                    Explosion newExplosion = new Explosion(power, x, y);
                    newExplosion.setPower(power);
                    newExplosion.setExplosionRange(explosionRange);
                    explosions.put(index, newExplosion);
                    backgroundCanvas.add(newExplosion);
                    newExplosion.startAnimation();
                }
            }).start();
        }

    }

    public HashMap<Integer, Item> getAllDynamicObjects() {
        return dynamicObjects;
    }

    public ConcurrentHashMap<Integer, VirtualCharacter> getCharacters() {
        return characters;
    }

    public ConcurrentHashMap<Integer, Bomb> getBombs() {
        return bombs;
    }

    public void keyPressed(KeyEvent e) {
        if (!backgroundCanvas.isWalkingAnimation) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    tcp.callAction(0);
                    break;
                case KeyEvent.VK_LEFT:
                    tcp.callAction(1);
                    break;
                case KeyEvent.VK_RIGHT:
                    tcp.callAction(2);
                    break;
                case KeyEvent.VK_UP:
                    tcp.callAction(3);
                    break;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                tcp.callAction(4);
        }
    }

    public int getLocalPlayerId() {
        return localPlayerId;
    }

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public void setPlayerName(int id, String name) {
        if (id != 2)
            sidebar.addAvatarBox(id, name);
        else
            sidebar.addAvatarBox(2, "YPC");
    }
}
