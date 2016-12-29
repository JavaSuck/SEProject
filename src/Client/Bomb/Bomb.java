package Client.Bomb;

import Client.Sprite.Animation;
import Client.Sprite.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bomb extends Sprite {

    private int id;
    private int positionX, positionY;
    private int delay = 100;
    private Animation explosion, bomb;

    public Bomb(int id) {
        this.id = id;
        loadSprite("bomb.png");
        for (int i = 0; i < 4; i++) {
            setSplitSize(47);
            BufferedImage[] bombs = {getSprite(0, 0), getSprite(0, 1), getSprite(0, 2), getSprite(0, 1)};
            bomb = new Animation(bombs, delay);
            animation = bomb;
            start();
        }
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

    protected void customRender(Graphics g) {

    }

    public int getId() {
        return id;
    }
}
