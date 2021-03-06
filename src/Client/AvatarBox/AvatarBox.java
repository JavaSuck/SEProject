package Client.AvatarBox;

import Client.ImagePanel.ImagePanel;

import javax.swing.*;

import java.awt.*;

public class AvatarBox extends JPanel {
    private String name = "";
    private int SIDEITEM_WIDTH = 160;
    private int SIDEITEM_HEIGHT = 167;
    private int AVATAR_SIZE = 155;
    private int AVATAR_CROP_SIZE = 144;
    private ImagePanel avatar;
    private JLabel label;
    private String avatarName = "";

    public AvatarBox(String name, String avatarName) {
        this.name = name;
        this.avatarName = avatarName;
        initUI();
        setLayout(new BorderLayout());
        avatar = new ImagePanel(avatarName, AVATAR_SIZE, AVATAR_SIZE, AVATAR_CROP_SIZE);

        label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        add(avatar, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);

    }

    private void initUI() {
        setPreferredSize(new Dimension(SIDEITEM_WIDTH, SIDEITEM_HEIGHT));
        setBackground(Color.WHITE);
    }

    public void setAVATAR_SIZE(int size) {
        this.AVATAR_SIZE = size;
    }

    public void updateAvatar(String avatarName) {
        if (!this.avatarName.equals(avatarName)) {
            this.avatarName = avatarName;

            avatar = new ImagePanel(avatarName, AVATAR_SIZE, AVATAR_SIZE, AVATAR_CROP_SIZE);
            removeAll();
            add(avatar, BorderLayout.CENTER);
            add(label, BorderLayout.SOUTH);
            revalidate();
            repaint();
        }
    }

    public void update() {
        removeAll();
        avatar = new ImagePanel(avatarName, AVATAR_SIZE, AVATAR_SIZE, AVATAR_CROP_SIZE);
        label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 24));
        add(avatar, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }
}
