package Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Client.Assets.Images.images;
import apple.laf.JRSUIUtils;

public class Sidebar extends JPanel {

    private final int width = 200;
    private final int height = 720;

    public Sidebar() {
        setBackground(Color.BLACK);
//        BufferedImage avatar = ImageIO.read(images.class.getResource("p1_face.png"));
//        ImagePanel test = new ImagePanel("p1_face.png");
        AvatarBox avatarbox = new AvatarBox("YPC", "p1_face.png");
//        add(test);
//        add(avatar);
        avatarbox.setSize(200, 200);
        add(avatarbox);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
