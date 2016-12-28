package Client.Bomb;

import java.awt.image.BufferedImage;

import Client.Sprite.Animation;
import Client.Sprite.Sprite;

public class ExplosionSprite extends Sprite {

    private final String imageName = "bomb.png";
    private final int delay = 30;

    public ExplosionSprite() {
        loadSprite(imageName);
    }

    public void createAnimation(BufferedImage[] frames) {
        animation = new Animation(frames, delay);
    }

}
