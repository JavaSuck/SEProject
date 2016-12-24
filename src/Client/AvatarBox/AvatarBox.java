package Client.AvatarBox;

import java.awt.*;

import javax.swing.*;

import Client.ImagePanel.ImagePanel;

public class AvatarBox extends JPanel {
    private String name = "";
    private int SIDEITEM_WIDTH = 160;
    private int SIDEITEM_HEIGHT = 167;
    private int AVATAR_SIZE = 155;
    private int AVATAR_CROP_SIZE = 144;

    public AvatarBox(String name, String avatarName) {

        initUI();
        setLayout(new BorderLayout());
        ImagePanel avatar = new ImagePanel(avatarName, AVATAR_SIZE, AVATAR_SIZE, AVATAR_CROP_SIZE);

        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        add(avatar, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);

    }

    private void initUI() {
        setPreferredSize(new Dimension(SIDEITEM_WIDTH, SIDEITEM_HEIGHT));
        setBackground(Color.WHITE);
    }
}
