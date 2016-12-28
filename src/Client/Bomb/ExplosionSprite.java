package Client.Bomb;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Client.Sprite.Animation;
import Client.Sprite.Sprite;
import Server.CDC.Direction;

public class ExplosionSprite extends Sprite implements Cloneable {

    private final String imageName = "bomb.png";
    private final int delay = 30;
    private ArrayList<Sprite> mid = new ArrayList<>(4);
    private ArrayList<Sprite> end = new ArrayList<>(4);
    private Sprite explosionSprite = new Sprite();

    public ExplosionSprite(Direction direction, boolean isEnd) {
        loadSprite(imageName);
        initSprites(direction, isEnd);
    }


    public BufferedImage[] getAnimationFrames(int column) {
        BufferedImage[] frames = {explosionSprite.getSprite(1, column), explosionSprite.getSprite(2, column), explosionSprite.getSprite(3, column), explosionSprite.getSprite(4, column)};
        return frames;
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
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
