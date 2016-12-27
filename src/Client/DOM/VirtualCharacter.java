package Client.DOM;

import Client.Scene.Game;
import Client.Sprite.Animation;
import Client.Sprite.Sprite;
import Server.CDC.Direction;

import java.awt.*;
import java.awt.image.BufferedImage;


public class VirtualCharacter extends Sprite {

    private Direction currentDirection = Direction.DOWN;

    private String name;
    private Point coordinateNext;
    private final int walkingFrameAmount = 6;

    private int delay = 8;


    Animation[] walk = new Animation[4];


    public VirtualCharacter(String imageName) {

        loadSprite(imageName);
        for (int i = 0; i < 4; i++) {
            BufferedImage[] walking = {getSprite(i, 1), getSprite(i, 0), getSprite(i, 2)};
            walk[i] = new Animation(walking, delay);
        }
        animation = walk[currentDirection.getValue()];
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
//        setLocation(direction.x, direction.y);
        currentDirection = direction;
        this.coordinateNext = coordinateNext;

        if(!shouldCharacterSync) {
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
