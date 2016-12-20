package Sprite;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * Created by DMOON on 2016/12/14.
 */
public class AnimationTest {

    Animation animation;

    @Before
    public void setup() throws Exception {
        Sprite sprite = new Sprite();
        sprite.loadSprite("sprite.png");
        BufferedImage[] walking = {sprite.getSprite(0, 0), sprite.getSprite(0, 1), sprite.getSprite(0, 2), sprite.getSprite(0, 3), sprite.getSprite(0, 4), sprite.getSprite(0, 5)};
        animation = new Animation(walking, 10);
    }

    @Test
    public void start() throws Exception {

    }

    @Test
    public void stop() throws Exception {

    }

    @Test
    public void restart() throws Exception {

    }

    @Test
    public void reset() throws Exception {

    }

    @Test
    public void getSprite() throws Exception {
        assertEquals(BufferedImage.class, animation.getSprite().getClass());
    }

    @Test
    public void update() throws Exception {

    }

}