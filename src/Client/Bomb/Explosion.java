package Client.Bomb;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import Client.Sprite.Sprite;
import Server.CDC.Direction;


public class Explosion extends JPanel {

    private int power = 2;
    private int size = 5;
    private HashMap<Integer, Sprite> explosion = new HashMap<>();
    private ArrayList<Sprite> mid = new ArrayList<>(4);
    private ArrayList<Sprite> end = new ArrayList<>(4);
    private Sprite center = new Sprite();

    private Sprite explosionSprite = new Sprite();
    private final String imageName = "bomb.png";

    public Explosion(int power) {
//        loadSprite("bomb.png");
    }

    public void setPower(int power) {
        this.power = power;
        size = power * 2 + 1;
    }

    public BufferedImage[] getAnimationFrames(int column) {
        BufferedImage[] frames = {explosionSprite.getSprite(1, 7), explosionSprite.getSprite(2, 7), explosionSprite.getSprite(3, 7), explosionSprite.getSprite(4, 7)};
        return frames;
    }

    public void initExplosionSprite() {
        explosionSprite.loadSprite(imageName);
        setSize(size, size);

        for (int i = 0; i < 4; i++) {
            ExplosionSprite newExplosionSprite = new ExplosionSprite();
            switch (Direction.getDirection(i)) {
                case DOWN:
                    newExplosionSprite.createAnimation(getAnimationFrames(7));
                    mid.set(i, newExplosionSprite);
                    newExplosionSprite.createAnimation(getAnimationFrames(8));
                    end.set(i, newExplosionSprite);
                    break;
                case LEFT:
                    newExplosionSprite.createAnimation(getAnimationFrames(3));
                    mid.set(i, newExplosionSprite);
                    newExplosionSprite.createAnimation(getAnimationFrames(2));
                    end.set(i, newExplosionSprite);
                    break;
                case RIGHT:
                    newExplosionSprite.createAnimation(getAnimationFrames(1));
                    mid.set(i, newExplosionSprite);
                    newExplosionSprite.createAnimation(getAnimationFrames(4));
                    end.set(i, newExplosionSprite);
                    break;
                case UP:
                    newExplosionSprite.createAnimation(getAnimationFrames(5));
                    mid.set(i, newExplosionSprite);
                    newExplosionSprite.createAnimation(getAnimationFrames(6));
                    end.set(i, newExplosionSprite);
                    break;
            }
        }
    }


//    public void setExplosionRange(int[] ) {
//
//        for (int i = 0; i < 4; i++) {
//
//        }
//
//    }


}
