package Client.Objects;

import Client.Sprite.Sprite;

public class Item extends Sprite {
    private int index;
    private String name;
    private boolean shared = false;
    private int owner;

    public Item(String n, int i, boolean s, String imgName) {
        loadSprite(imgName);
        index = i;
        name = n;
        shared = s;
    }

    public void updateItem(boolean s, int own) {
        shared = s;
        owner = own;
    }

    public String getName() {
        return name;
    }

    public boolean getShared() {
        return shared;
    }

    public void draw() {
        System.out.print("draw");
    }

    public int getOwner() {
        return owner;
    }
}
