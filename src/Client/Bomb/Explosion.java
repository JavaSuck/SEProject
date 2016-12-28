package Client.Bomb;

import java.util.HashMap;

import javax.swing.*;

import Client.Sprite.Sprite;


public class Explosion extends Sprite {

    private int power = 2;
    private HashMap<Integer, Sprite> explosion = new HashMap<>();

    public Explosion() {
        loadSprite("bomb.png");
    }

    public void initExplosion() {
        for (int i = 0; i < Math.pow(2 * power + 1, 2); i++) {

        }
    }
}
