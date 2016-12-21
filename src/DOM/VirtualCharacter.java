package DOM;

import java.awt.*;
import java.awt.image.BufferedImage;

import Sprite.Sprite;
import Sprite.Animation;

public class VirtualCharacter extends Sprite {

    private final int D_LEFT = 1,
            D_RIGHT = 2,
            D_DOWN = 0,
            D_UP = 3;

    private int currentDirection = D_DOWN;

    private String name;
    private int positionX, positionY;
    private int speed;
    private final int walkingFrameAmount = 6;

    private int delay = 12;

    Animation[] walk = new Animation[4];

    // current animation
    private Animation animation;

    public VirtualCharacter(String imageName) {
        loadSprite(imageName);
        for (int i = 0; i < 4; i++) {
            BufferedImage[] walking = {getSprite(i, 0), getSprite(i, 1), getSprite(i, 2)};
            walk[i] = new Animation(walking, delay);
        }
        animation = walk[D_DOWN];
    }

    public Point getPosition() {
        return new Point(positionX, positionY);
    }

    public void walk(int dir) {
        animation = walk[dir];
        currentDirection = dir;
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

    public void updateCharacter(int dir, int sp, int x, int y) {
        currentDirection = dir;
        speed = sp;
        positionX = x;
        positionY = y;
        walk(dir);
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public int getSpeed() {
        return speed;
    }

    public BufferedImage getAnimationFrame() {
        return animation.getSprite();
    }

}
