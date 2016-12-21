package Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;

import Client.Assets.Images.images;

import static com.sun.deploy.ui.AppInfo.ICON_SIZE;

public class AvatarBox extends JPanel {
    private String text = "";
    private int ICON_SIZE = 200;
    private int SIDEITEM_WIDTH = 180;
    private int SIDEITEM_HEIGHT = 180;

    public AvatarBox(String name, String avatarName) {

        initUI();
        setLayout(new BorderLayout());
        ImagePanel avatar = new ImagePanel(avatarName);

        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        add(avatar, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);

    }

    private void initUI() {
        setSize(SIDEITEM_WIDTH, SIDEITEM_HEIGHT);
        setBackground(Color.WHITE);
    }
}
