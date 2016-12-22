package Client.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Assets.Images.images;
import Client.Client;
import Client.Sprite.*;

public class ImagePanel extends JPanel {

    private BufferedImage srcImage;
    private Image resizedImage;
    private int resizedWidth = 150;
    private int resizedHeight = 150;
    private int splitSize = 144;

    public ImagePanel(String fileName) {
        setPreferredSize(new Dimension(resizedWidth, resizedHeight));
        Sprite avatarSprite = new Sprite();
        avatarSprite.loadSprite(fileName);
        avatarSprite.setSplitSize(splitSize);
        srcImage = avatarSprite.getSprite(0, 0);
        resizedImage = srcImage.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resizedImage, 0, 0, this); // see javadoc for more info on the parameters
    }

    public void setSplitSize(int size) {
        splitSize = size;
    }

    public void setResizedSize(int width, int height) {
        resizedWidth = width;
        resizedHeight = height;
    }
}
