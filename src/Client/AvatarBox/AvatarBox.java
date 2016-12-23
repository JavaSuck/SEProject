package Client.AvatarBox;

import java.awt.*;

import javax.swing.*;

import Client.ImagePanel.ImagePanel;

public class AvatarBox extends JPanel {
    private String name = "";
    private int ICON_SIZE = 200;
    private int SIDEITEM_WIDTH = 160;
    private int SIDEITEM_HEIGHT = 160;

    public AvatarBox(String name, String avatarName) {

        initUI();
        setLayout(new BorderLayout());
        ImagePanel avatar = new ImagePanel(avatarName);
        avatar.setResizedSize(150, 150);
        avatar.setSplitSize(144);

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
