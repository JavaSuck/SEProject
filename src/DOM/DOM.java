package DOM;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import Objects.Item;
import TCP.TCPCM;


public class DOM {

    private TCPCM tcpcm;
    private HashMap<Integer, Item> dynamicObjects = new HashMap<Integer, Item>();
    private HashMap<Integer, VirtualCharacter> characters = new HashMap<Integer, VirtualCharacter>();

    private int myClientno = 0;

    public void addVirtualCharacter(int clientno) {
        try {
            // create VirtualCharacter and Sprite
            VirtualCharacter character = new VirtualCharacter("sprite.png");
            characters.put(clientno, character);
        } catch (Exception e) {
            throw e;
        }
    }

    public Point getVirtualCharacterXY() {
        return characters.get(myClientno).getPosition();
    }

    public void updateVirtualCharacter(int clientno, int dir, int speed, int x, int y) {
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

    public void setTcpcm(TCPCM cm) {
        try {
            tcpcm = cm;
        } catch (Exception e) {
            throw e;
        }
    }

    public void keyGETPressed(String act) {
        switch (act) {
            case "L":
                tcpcm.inputMoves(0);
                break;
            case "R":
                tcpcm.inputMoves(1);
                break;
        }
    }
}
