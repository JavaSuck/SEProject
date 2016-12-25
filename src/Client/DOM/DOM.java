package Client.DOM;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.stream.Collector;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.Objects.Item;
import Client.TCPClient.TCPClient;
import Server.CDC.Direction;


public class DOM {

    private TCPClient tcpcm;
    private HashMap<Integer, Item> dynamicObjects = new HashMap<Integer, Item>();
    private HashMap<Integer, VirtualCharacter> characters = new HashMap<Integer, VirtualCharacter>();
    private BackgroundCanvas backgroundCanvas;
    private VirtualCharacter character;

    private int myClientno = 0;

    public void addVirtualCharacter(int clientno) {
        try {
            // create VirtualCharacter and Client.Sprite
            VirtualCharacter character = new VirtualCharacter("sprite.png");
            characters.put(clientno, character);
        } catch (Exception e) {
            throw e;
        }
    }

    public Point getVirtualCharacterXY() {
        return characters.get(myClientno).getPosition();
    }

    public void updateVirtualCharacter(int clientno, Direction dir, int speed, int x, int y) {
        try {
            characters.get(clientno).updateCharacter(dir, speed, x, y);
        } catch (Exception e) {
            throw e;
        }
    }

    /* Item */
    public void addItem(String name, int index, boolean shared) {
        try {
            Item newItem = new Item(name, index, shared, "item.png");
            if (dynamicObjects.get(index) == null) {
                dynamicObjects.put(index, newItem);
            } else {
                System.out.println("Create Failed: item already exist");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateItem(int index, boolean shared, int owner) {
        try {
            dynamicObjects.get(index).updateItem(shared, owner);
        } catch (Exception e) {
            throw e;
        }
    }

    public HashMap<Integer, Item> getAllDynamicObjects() {
        return dynamicObjects;
    }

    public HashMap<Integer, VirtualCharacter> getCharacters() {
        return characters;
    }

    public void setMyClientno(int clientno) {
        myClientno = clientno;
    }

    public void setTcpcm(TCPClient cm) {
        try {
            tcpcm = cm;
        } catch (Exception e) {
            throw e;
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                character.walk(Direction.LEFT);
                backgroundCanvas.moveCanvas(48, 0);
                break;
            case KeyEvent.VK_RIGHT:
                character.walk(Direction.RIGHT);
                backgroundCanvas.moveCanvas(-48, 0);
                break;
            case KeyEvent.VK_UP:
                character.walk(Direction.UP);
                backgroundCanvas.moveCanvas(0, 48);
                break;
            case KeyEvent.VK_DOWN:
                backgroundCanvas.moveCanvas(0, -48);
                character.walk(Direction.DOWN);
                break;
            case KeyEvent.VK_SPACE:
                character.stop();
                break;
            default:
                break;
        }
    }

    public void setBackgroundCanvas(BackgroundCanvas backgroundCanvas) {
        this.backgroundCanvas = backgroundCanvas;
    }

    public void setCharacter(VirtualCharacter character) {
        this.character = character;
    }
}
