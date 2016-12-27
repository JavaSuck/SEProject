package Client.DOM;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.Objects.Item;
import Client.TCPClient.TCPClient;
import Server.CDC.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;


public class DOM {
  private TCPClient tcp;
  private HashMap<Integer, Item> dynamicObjects = new HashMap<>();
  private HashMap<Integer, VirtualCharacter> characters = new HashMap<>();
  private BackgroundCanvas backgroundCanvas;
  private int localPlayerId;
  public VirtualCharacter localPlayer;

  public DOM(TCPClient tcp, BackgroundCanvas backgroundCanvas) {
    this.tcp = tcp;
    this.backgroundCanvas = backgroundCanvas;
    this.localPlayerId = tcp.playerId;

    for (int i = 0; i < 4; i++) {
      VirtualCharacter character = new VirtualCharacter("player" + i + ".png");

      characters.put(i, character);
      if (i == localPlayerId)
        localPlayer = character;
      else
        backgroundCanvas.add(character);
    }
  }

  public Point getVirtualCharacterXY() {
    return characters.get(localPlayerId).getPosition();
  }

  public void updateVirtualCharacter(int playerId, Direction dir, int x, int y, boolean isWalk) {
    characters.get(playerId).updateCharacter(dir, x, y, isWalk);
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
    switch (e.getKeyCode()) {
      case KeyEvent.VK_SPACE:
//                tcp.callAction(4);
//                localPlayer.stop();
    }
  }
}
