package Client.DOM;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.Objects.Item;
import Server.CDC.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class DOM {

    private HashMap<Integer, Item> dynamicObjects = new HashMap<>();
    private HashMap<Integer, VirtualCharacter> characters = new HashMap<>();
    private BackgroundCanvas backgroundCanvas;
    private VirtualCharacter character;
    private int myClientno = 0;

    public DOM(BackgroundCanvas backgroundCanvas, VirtualCharacter character) {
        this.backgroundCanvas = backgroundCanvas;
        this.character = character;
    }

    public void addVirtualCharacter(int clientno) {
        // Create VirtualCharacter and Client.Sprite
        VirtualCharacter character = new VirtualCharacter("sprite.png");
        characters.put(clientno, character);
    }

    public Point getVirtualCharacterXY() {
        return characters.get(myClientno).getPosition();
    }

    public void updateVirtualCharacter(int clientno, Direction dir, int speed, int x, int y) {
        characters.get(clientno).updateCharacter(dir, speed, x, y);
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

    public HashMap<Integer, Item> getAllDynamicObjects() {
        return dynamicObjects;
    }

    public HashMap<Integer, VirtualCharacter> getCharacters() {
        return characters;
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
}
