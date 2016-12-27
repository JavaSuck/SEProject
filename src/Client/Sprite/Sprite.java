package Client.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Assets.Images.images;

public class Sprite extends JPanel {

    private BufferedImage spriteSheet;
    private int tileSize = 48;
    protected BufferedImage currentFrame;
    protected Animation animation;

    public Sprite() {
        setSize(tileSize, tileSize);
    }

    public BufferedImage loadSprite(String file) {

//        BufferedImage sprite = null;

        try {
            spriteSheet = ImageIO.read(images.class.getResource(file));
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

    public void setSplitSize(int size) {
        tileSize = size;
        setSize(tileSize, tileSize);
        setPreferredSize(new Dimension(tileSize, tileSize));
    }

//    protected abstract void customRender(Graphics g);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
//        customRender(g);
        if (animation != null) {
            g.drawImage(animation.getSprite(), 0, 0, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(tileSize, tileSize);
    }
}