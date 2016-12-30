package Client.Scene;

import java.awt.*;
import java.util.Arrays;

import javax.swing.*;

import Client.AvatarBox.AvatarBox;

import Client.ImagePanel.ImagePanel;

public class Result extends JPanel {
    private int height = 720;
    private int width = 900;
    private String[] name = new String[4];
    private int avatarSize = 130;
    private int avatarBoxSize = 150;
    private int interval = 50;

    public Result(String[] playerName, int[] playerTime) {
        setLayout(null);
        setSize(width, height);
        ImagePanel Background = new ImagePanel("login_bg.jpg", height, width);

        setBackground(Color.BLACK);
//
        JLabel NameLabel = new JLabel("Result");
        NameLabel.setFont(new Font("Serif", Font.PLAIN, 60));
        NameLabel.setBounds((900 - 180) / 2, 60, 180, 50);
        NameLabel.setForeground(Color.WHITE);
        add(NameLabel);


        Background.setBounds(0, 0, height, width);

        AvatarBox[] avatarBoxes = new AvatarBox[4];
        for (int i = 0; i < 4; i++) {
            avatarBoxes[i] = new AvatarBox(playerName[i], "avatar" + i + ".png");
            avatarBoxes[i].setSize(avatarSize, avatarSize);
            avatarBoxes[i].update();
            avatarBoxes[i].setBounds(80 + i * avatarBoxSize + i * interval, 200, avatarBoxSize, avatarBoxSize);
            add(avatarBoxes[i]);
        }

        int prize[] = new int[4];
//
        int temp[] = new int[4];
        for (int i = 0; i < 4; i++) {
            temp[i] = playerTime[i];
            prize[i] = 0;
        }
        Arrays.sort(temp);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (temp[i] == playerTime[j]) {
                    prize[i] = 5 - j + 1;
                }
            }
        }

        JLabel[] prizeLabels = new JLabel[4];
        for (int i = 0; i < 4; i++) {
            prizeLabels[i] = new JLabel("第 " + prize[i] + " 名");
            prizeLabels[i].setFont(new Font("Serif", Font.PLAIN, 20));
//            prize[i].setBounds((900 - 180) / 2, (60) / 2, avatarBoxSize, avatarBoxSize);
            prizeLabels[i].setForeground(Color.WHITE);
            prizeLabels[i].setBounds(80 + (avatarBoxSize - 60) / 2 + i * avatarBoxSize + i * interval, 400, avatarBoxSize, avatarBoxSize);
            add(prizeLabels[i]);
        }


    }
}
