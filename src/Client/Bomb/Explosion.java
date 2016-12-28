package Client.Bomb;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import Client.Scene.Game;
import Client.Sprite.Sprite;
import Server.CDC.Direction;


public class Explosion extends JPanel {

    private int power = 2;
    private int size = 5;
    private HashMap<Integer, Sprite> explosion = new HashMap<>();
    private ArrayList<Sprite> mid = new ArrayList<>(4);
    private ArrayList<Sprite> end = new ArrayList<>(4);
    private Sprite center = new Sprite();
    private int centerX, centerY;
    private Sprite explosionSprite = new Sprite();

    public Explosion(int power) {

    }

    public void setPower(int power) {
        this.power = power;
        size = power * 2 + 1;
    }


    public void setExplosionRange(int[] explosionRange) {
        for (int i = 0; i < 4; i++) {
            int powerLength = 0;
            Direction direction = Direction.getDirection(i);
            switch (direction) {
                case DOWN:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, 0, 1);
                    break;
                case RIGHT:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, 1, 0);
                    break;
                case UP:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, 0, -1);
                    break;
                case LEFT:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, -1, 0);
                    break;
            }

        }
    }

    public void createRange(int powerLength, Direction direction, int xDir, int yDir) {
        for (int j = 0; j < powerLength - 1; j++) {
            // mid
            ExplosionSprite newMidSprite = new ExplosionSprite(direction, false);
            newMidSprite.setLocation((centerX + j * xDir) * Game.BLOCK_PIXEL, (centerY + j * yDir) * Game.BLOCK_PIXEL);
            add(newMidSprite);
        }
        // end
        ExplosionSprite endMidSprite = new ExplosionSprite(direction, true);
        endMidSprite.setLocation((centerX + powerLength * xDir) * Game.BLOCK_PIXEL, (centerY + powerLength * yDir) * Game.BLOCK_PIXEL);
        add(endMidSprite);
    }
}
