package Client.Scene;

import java.awt.*;

import javax.swing.*;

import Client.Client;
import Client.ImagePanel.ImagePanel;

public class Result extends JPanel {
    private int height = 720;
    private int width = 900;

    public Result() {
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
    }
}
