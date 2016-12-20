package Sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private BufferedImage spriteSheet;
    private final int TILE_SIZE = 32;

    protected BufferedImage loadSprite(String file) {

        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(images.images.class.getResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {

        // default image
        if (spriteSheet == null) {
            spriteSheet = loadSprite("sprite.png");
        }

        return spriteSheet.getSubimage(yGrid * TILE_SIZE, xGrid * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

}