package Client.Scene;

import java.awt.*;

import javax.swing.*;

import Client.AvatarBox.AvatarBox;

import Client.ImagePanel.ImagePanel;

public class Result extends JPanel {
    private int height = 720;
    private int width = 900;
    private String[] name = new String[4];
    private int avatarSize = 150;

    public Result(String[] playerName, int[] playerTime) {
        setLayout(null);
        setSize(width, height);
        ImagePanel Background = new ImagePanel("login_bg.jpg", height, width);

        setBackground(Color.BLACK);

        JLabel NameLabel = new JLabel("Result");
        NameLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        NameLabel.setBounds((900 - 180) / 2, (80) / 2, 180, 50);
        NameLabel.setForeground(Color.WHITE);

        Background.setBounds(0, 0, height, width);
        add(NameLabel);

        AvatarBox[] avatarBoxes = new AvatarBox[4];
        for (int i = 0; i < 4; i++) {
            avatarBoxes[i] = new AvatarBox("name", "avatar" + i + ".png");
//            avatarBoxes[i].setSize(avatarSize, avatarSize);
//            avatarBoxes[i].update();
            avatarBoxes[i].setLocation(80, 40 + i * avatarBoxes[i].getHeight());
//            avatarBoxes[i].setBounds(80, 40 + i * avatarBoxes[i].getHeight(), avatarSize, avatarSize);
            add(avatarBoxes[i]);
        }
    }
}
