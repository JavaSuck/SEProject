import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Assets.Images.images;
import Client.Sprite.*;

public class ImagePanel extends JPanel {

    private BufferedImage srcImage;
    private Image resizedImage;
    private int resizedWidth = 180;
    private int resizedHeight = 180;

    public ImagePanel(String fileName) {
//        try {
        setPreferredSize(new Dimension(resizedWidth, resizedHeight));
        //setBackground(Color.RED);
//            srcImage = ImageIO.read(images.class.getResource(fileName));
        Sprite avatarSprite = new Sprite();
        avatarSprite.loadSprite(fileName);
        avatarSprite.setSplitSize(144);
        srcImage = avatarSprite.getSprite(0, 0);
        resizedImage = srcImage.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
//        } catch (IOException ex) {
//             handle exception...
//            System.out.print("error");
//        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resizedImage, 0, 0, this); // see javadoc for more info on the parameters
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(resizedWidth, resizedHeight);
//    }
}
