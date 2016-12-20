package Sprite;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;


public class SpriteTest {


    Sprite sprite;

    @Before
    public void setup() throws Exception {
        sprite = new Sprite();
    }

    @Test
    public void loadSprite() throws Exception {
        assertEquals(BufferedImage.class, sprite.loadSprite("sprite.png").getClass());
    }

    @Test
    public void getSprite() throws Exception {
        assertEquals(BufferedImage.class, sprite.getSprite(0, 1).getClass());
    }

}