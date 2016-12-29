package Client.Bomb;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import Client.Sprite.Animation;
import Client.Sprite.Sprite;
import Server.CDC.Direction;

public class ExplosionSprite extends Sprite {

    private final String imageName = "bomb.png";
    private final int delay = 100;
    private int frameCount = 0;

    public ExplosionSprite(Direction direction, boolean isEnd) {
        loadSprite(imageName);
        if (direction != null) {
            initSprites(direction, isEnd);
        }
    }


    public int getTotalFrames() {
        return animation.getTotalFrames();
    }

    public BufferedImage[] getAnimationFrames(int column) {
        BufferedImage[] frames = {getSprite(1, column), getSprite(2, column), getSprite(3, column), getSprite(4, column), getSprite(4, column), getSprite(4, column), getSprite(3, column), getSprite(2, column), getSprite(1, column)};
        frameCount = frames.length;
        return frames;
    }

    public void initCenter() {
        createAnimation(getAnimationFrames(0));
    }

    public void initSprites(Direction direction, boolean isEnd) {
        int midColumn = 0, endColumn = 0;
        switch (direction) {
            case DOWN:
                midColumn = 7;
                endColumn = 8;
                break;
            case LEFT:
                midColumn = 3;
                endColumn = 2;
                break;
            case RIGHT:
                midColumn = 1;
                endColumn = 4;
                break;
            case UP:
                midColumn = 5;
                endColumn = 6;
                break;
        }

        if (isEnd) {
            createAnimation(getAnimationFrames(endColumn));
        } else {
            createAnimation(getAnimationFrames(midColumn));
        }
    }

    public void createAnimation(BufferedImage[] frames) {
        animation = new Animation(frames, delay);
        animation.setIsRepeat(false);
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

    public int getCurrentFrame() {
        return animation.getCurrentFrame();
    }

}
