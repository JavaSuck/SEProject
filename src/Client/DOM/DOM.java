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

    private HashMap<Integer, VirtualCharacter> characters = new HashMap<>();
    private HashMap<Integer, Bomb> bombs = new HashMap<>();
    private ConcurrentHashMap<Integer, Explosion> explosions = new ConcurrentHashMap<>();
    private BackgroundCanvas backgroundCanvas;
    private Sidebar sidebar;

    private int localPlayerId;
    public VirtualCharacter localPlayer;

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

    public void addItem(String name, int index, boolean shared) {
        Item newItem = new Item(name, index, shared, "item.png");
        if (dynamicObjects.get(index) == null) {
            dynamicObjects.put(index, newItem);
        } else {
            System.out.println("Create Failed: item already exist");
        }
    }

    public void updateItem(int index, boolean shared, int owner) {
        dynamicObjects.get(index).updateItem(shared, owner);
    }

    public void updateBomb(int index, int x, int y, boolean isExist, int[] explosionRange, int power) {
        new Thread(() -> {
            // Create exist bomb
            if (bombs.get(index) == null && isExist) {
                Bomb newBomb = new Bomb(index);
                newBomb.setLocation(x * Game.BLOCK_PIXEL, y * Game.BLOCK_PIXEL);
                bombs.put(index, newBomb);
                backgroundCanvas.add(newBomb);
            }
            // Exist, do explode
            else if (bombs.get(index) != null && !isExist) {
                Bomb bomb = bombs.get(index);
                bomb.stop();
                backgroundCanvas.remove(bomb);
                bombs.remove(bomb.getId());
                Explosion newExplosion = new Explosion(power, x, y);
                newExplosion.setPower(power);
                newExplosion.setExplosionRange(explosionRange);
                explosions.put(index, newExplosion);
                backgroundCanvas.add(newExplosion);
                newExplosion.startAnimation();
            }

        }).start();
    }

    public void bombExplode(int index) {
        if (bombs.get(index) == null) {
//            bombs.get(index);
//            create Explosion(int x, int y);
//            backgroundCanvas.add(explosion);
//            explosion.start();
        }
    }

    public HashMap<Integer, Item> getAllDynamicObjects() {
        return dynamicObjects;
    }

    public HashMap<Integer, VirtualCharacter> getCharacters() {
        return characters;
    }

    public HashMap<Integer, Bomb> getBombs() {
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
