package Sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    private BufferedImage spriteSheet;
    private int tileSize = 48;

    protected BufferedImage loadSprite(String file) {

//        BufferedImage sprite = null;

        try {
            spriteSheet = ImageIO.read(images.images.class.getResource(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return spriteSheet;
    }

    public BufferedImage getSprite(int xGrid, int yGrid) {

        // default image
        if (spriteSheet == null) {
            spriteSheet = loadSprite("sprite.png");
        }

        return spriteSheet.getSubimage(yGrid * tileSize, xGrid * tileSize, tileSize, tileSize);
    }

    public void setTileSize(int size) {
        tileSize = size;
    }

}