package Client.Bomb;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import Client.Sprite.Animation;
import Client.Sprite.Sprite;

public class Bomb extends Sprite {
    private int positionX, positionY;
    private int delay = 20;
    private Animation explosion, bomb;

    public Bomb() {
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
}
