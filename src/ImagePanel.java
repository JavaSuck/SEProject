import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Assets.Images.images;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    public ImagePanel(String fileName) {
        try {
            image = ImageIO.read(images.class.getResource(fileName));
        } catch (IOException ex) {
            // handle exception...
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
    }
}
