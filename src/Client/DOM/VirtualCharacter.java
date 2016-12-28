package Client.DOM;

import Client.Scene.Game;
import Client.Sprite.Animation;
import Client.Sprite.Sprite;
import Server.CDC.Direction;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class VirtualCharacter extends Sprite {

    private Direction currentDirection = Direction.DOWN;

    private String name;
    private Point coordinateNext;
    private final int walkingFrameAmount = 6;

    private int delay = 8;
    Animation[] walk = new Animation[4];

    private Timer timer;
    public Point coordinateOld = new Point(0, 0);
    private int newSpiteX;
    private int newSpiteY;
    private boolean shouldCharacterSync = true;


    public VirtualCharacter(String imageName) {

        loadSprite(imageName);
        for (int i = 0; i < 4; i++) {
            BufferedImage[] walking = {getSprite(i, 1), getSprite(i, 0), getSprite(i, 2)};
            walk[i] = new Animation(walking, delay);
        }
        animation = walk[currentDirection.getValue()];
    }

    public void initTimer() {
        ActionListener move = e -> {
            int oldSpriteX = getLocation().x;
            int oldSpriteY = getLocation().y;

            if (this.shouldCharacterSync) {
                setLocation(newSpiteX, newSpiteY);
                return;
            }

            int movePixel = 4;
//            if (Math.abs(newSpiteX - oldSpriteX) > 60 || Math.abs(newSpiteY - oldSpriteY) > 60) {
//                timer.setDelay(2);
//            } else if (Math.abs(newSpiteX - oldSpriteX) > 48 || Math.abs(newSpiteY - oldSpriteY) > 48) {
//                timer.setDelay(6);
//            } else {
//                timer.setDelay(8);
//            }

            if (newSpiteX - oldSpriteX > 0) {
                moveSprite(movePixel, 0);
            } else if (newSpiteX - oldSpriteX < 0) {
                moveSprite(movePixel * -1, 0);
            } else if (newSpiteY - oldSpriteY > 0) {
                moveSprite(0, movePixel);
            } else if (newSpiteY - oldSpriteY < 0) {
                moveSprite(0, movePixel * -1);
            }
        };

        timer = new Timer(16, move);
        timer.setRepeats(true);
        timer.start();
    }

//    public void initCanvasPosition() {
//        newCanvasX = canvasBasicOffsetX - coordinateOld.x * Game.BLOCK_PIXEL;
//        newCanvasY = canvasBasicOffsetY - coordinateOld.y * Game.BLOCK_PIXEL;
//        setLocation(newCanvasX, newCanvasY);
//    }

    public void moveSprite(int xOffsetDelta, int yOffsetDelta) {
        setLocation(getLocation().x + xOffsetDelta, getLocation().y + yOffsetDelta);
    }

    public Point getPosition() {
        return this.coordinateNext;

    }

    public void walk(Direction direction) {
        animation = walk[direction.getValue()];
        currentDirection = direction;
        start();
    }

    public void start() {
        animation.start();
    }

    public void stop() {
        animation.stop();
        animation.reset();
    }

    public void updateAnimation() {
        animation.update();
    }

    public void updateCharacter(Direction direction, Point coordinateNext, boolean shouldCharacterSync) {
        currentDirection = direction;
        this.shouldCharacterSync = shouldCharacterSync;
        if (coordinateOld.x != coordinateNext.x || coordinateOld.y != coordinateNext.y) {
            coordinateOld = coordinateNext;

            newSpiteX = coordinateNext.x * Game.BLOCK_PIXEL;
            newSpiteY = coordinateNext.y * Game.BLOCK_PIXEL;
        }
        setLocation(newSpiteX, newSpiteY);

        if (!shouldCharacterSync) {
            walk(direction);
        } else {
            stop();
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public BufferedImage getAnimationFrame() {
        return animation.getSprite();
    }


}
