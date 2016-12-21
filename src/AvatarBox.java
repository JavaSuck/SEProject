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
    private int SIDEITEM_HEIGHT = 200;

    public AvatarBox(String name, String avatarName) {
        initUI();
        ImagePanel avatar = new ImagePanel(avatarName);
        JLabel label = new JLabel(name);
        add(avatar);
        label.setLocation(0, 180);
        add(label);
    }

    private void initUI() {
        setSize(SIDEITEM_WIDTH, SIDEITEM_HEIGHT);
        setBackground(Color.WHITE);
//        setBackground(Color.WHITE);
//        setBorder(new LineBorder(Color.DARK_GRAY, 4));
//        setContentAreaFilled(false);
//        setFocusPainted(true);
//        setOpaque(true);
//        setSize(SIDEITEM_WIDTH, SIDEITEM_HEIGHT);
//        setEnabled(false);
//
//        setVerticalTextPosition(SwingConstants.BOTTOM);
//        setHorizontalTextPosition(SwingConstants.CENTER);
    }
}
