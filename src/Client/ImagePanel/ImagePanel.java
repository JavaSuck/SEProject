package Client.ImagePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Sprite.*;

public class ImagePanel extends JPanel {

    private BufferedImage srcImage;
    private Image resizedImage;
    private int resizedWidth = 150;
    private int resizedHeight = 150;
    private int cropSize = 50;

    public ImagePanel(String fileName, int width, int height) {
        initUI(width, height);
        try {
            srcImage = ImageIO.read(new File("src/Client/Assets/Images/" + fileName));
        } catch (Exception ex) {
            System.out.println("Can't find " + fileName + "in Assets/Images/");
        }
        resizeSrcImage();
    }

    public ImagePanel(String fileName, int width, int height, int cropSize) {
        initUI(width, height);
        Sprite avatarSprite = new Sprite();
        this.cropSize = cropSize;
        avatarSprite.setSplitSize(cropSize);
        avatarSprite.loadSprite(fileName);
        srcImage = avatarSprite.getSprite(0, 0);
        resizeSrcImage();
    }

    private void initUI(int width, int height) {
        setResizedSize(width, height);
    }

    private void resizeSrcImage() {
        resizedImage = srcImage.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resizedImage, 0, 0, this); // see javadoc for more info on the parameters
    }

    public void setCropSize(int size) {
        cropSize = size;
    }

    public void setResizedSize(int width, int height) {
        resizedWidth = width;
        resizedHeight = height;
        repaint();
    }
}
