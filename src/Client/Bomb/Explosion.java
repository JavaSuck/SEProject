package Client.Bomb;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import Client.DOM.VirtualCharacter;
import Client.Scene.Game;
import Client.Sprite.Sprite;
import Server.CDC.Direction;


public class Explosion extends JPanel {

    private int power = 4;
    private int size = 5;
    private HashMap<Integer, Sprite> explosion = new HashMap<>();
    private HashMap<Integer, ExplosionSprite> midUp = new HashMap<>();
    private HashMap<Integer, ExplosionSprite> midDown = new HashMap<>();
    private HashMap<Integer, ExplosionSprite> midLeft = new HashMap<>();
    private HashMap<Integer, ExplosionSprite> midRight = new HashMap<>();
    private HashMap<Integer, ExplosionSprite> end = new HashMap<>(4);
    private ExplosionSprite center;
    private int centerX, centerY;
    public int frameCount = 0;
    private boolean isStarted = false;

    public Explosion(int power, int centerX, int centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        setLayout(null);
        setOpaque(false);
        setLocation((centerX - power) * Game.BLOCK_PIXEL, (centerY - power) * Game.BLOCK_PIXEL);
        center = new ExplosionSprite(null, false);
        center.initCenter();
        center.setLocation(power * Game.BLOCK_PIXEL, power * Game.BLOCK_PIXEL);
        add(center);
    }

    public void setPower(int power) {
        this.power = power;
        reSize();
    }

    public void reSize() {
        size = power * 2 + 1;
        setSize(size * Game.BLOCK_PIXEL, size * Game.BLOCK_PIXEL);
    }

    public void setExplosionRange(int[] explosionRange) {
        reSize();
        for (int i = 0; i < 4; i++) {
            int powerLength = 0;
            Direction direction = Direction.getDirection(i);
            switch (direction) {
                case DOWN:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, 0, 1, i);
                    break;
                case RIGHT:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, 1, 0, i);
                    break;
                case UP:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, 0, -1, i);
                    break;
                case LEFT:
                    powerLength = explosionRange[i];
                    createRange(powerLength, direction, -1, 0, i);
                    break;
            }
        }
    }

    public void createRange(int powerLength, Direction direction, int xDir, int yDir, int dir) {

        for (int j = 1; j <= powerLength; j++) {
            if (power == j) {
                // end
                ExplosionSprite endMidSprite = new ExplosionSprite(direction, true);
                endMidSprite.setLocation((power + powerLength * xDir) * Game.BLOCK_PIXEL, (power + powerLength * yDir) * Game.BLOCK_PIXEL);
                end.put(dir, endMidSprite);
                add(endMidSprite);
            } else {
                // mid
                ExplosionSprite newMidSprite = new ExplosionSprite(direction, false);
                newMidSprite.setLocation((power + j * xDir) * Game.BLOCK_PIXEL, (power + j * yDir) * Game.BLOCK_PIXEL);
                switch (Direction.getDirection(dir)) {
                    case DOWN:
                        midDown.put(midDown.size(), newMidSprite);
                        break;
                    case UP:
                        midUp.put(midUp.size(), newMidSprite);
                        break;
                    case LEFT:
                        midLeft.put(midLeft.size(), newMidSprite);
                        break;
                    case RIGHT:
                        midRight.put(midRight.size(), newMidSprite);
                        break;

                }
                add(newMidSprite);
            }
        }

    }

    public void updateAnimation() {
        center.updateAnimation();
        for (Map.Entry<Integer, ExplosionSprite> m : midDown.entrySet()) {
            m.getValue().updateAnimation();
        }
        for (Map.Entry<Integer, ExplosionSprite> m : midLeft.entrySet()) {
            m.getValue().updateAnimation();
        }
        for (Map.Entry<Integer, ExplosionSprite> m : midRight.entrySet()) {
            m.getValue().updateAnimation();
        }
        for (Map.Entry<Integer, ExplosionSprite> m : midUp.entrySet()) {
            m.getValue().updateAnimation();
        }
        for (Map.Entry<Integer, ExplosionSprite> e : end.entrySet()) {
            e.getValue().updateAnimation();
        }
    }


    public void startAnimation() {
        center.start();
        for (Map.Entry<Integer, ExplosionSprite> m : midDown.entrySet()) {
            m.getValue().start();
        }
        for (Map.Entry<Integer, ExplosionSprite> m : midUp.entrySet()) {
            m.getValue().start();
        }
        for (Map.Entry<Integer, ExplosionSprite> m : midLeft.entrySet()) {
            m.getValue().start();
        }
        for (Map.Entry<Integer, ExplosionSprite> m : midRight.entrySet()) {
            m.getValue().start();
        }
        for (Map.Entry<Integer, ExplosionSprite> e : end.entrySet()) {
            e.getValue().start();
        }
    }

    public int getCurrentFrame() {
        return center.getCurrentFrame();
    }

    public int getFrameCount() {
        return center.getFrameCount();
    }

}
