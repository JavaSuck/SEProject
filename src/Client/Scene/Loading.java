package Client.Scene;

import Client.ImagePanel.ImagePanel;
import Client.TCPClient.TCPClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Loading extends JPanel {

    private int height = 720;
    private int width = 900;

    public Loading(TCPClient tcp) {
        setLayout(null);
        setSize(900, 720);

        ImagePanel Background = new ImagePanel("login_bg.jpg", height, width);

        setBackground(Color.BLACK);

        JLabel NameLabel = new JLabel("Loading...");
        NameLabel.setFont(new Font("Serif", Font.PLAIN, 35));
        NameLabel.setBounds((900 - 150) / 2, (720 - 40) / 2, 150, 40);
        NameLabel.setForeground(Color.WHITE);

        ActionListener taskPerformer = evt -> {
            tcp.getGameState();
        };
        Timer timer = new Timer(500, taskPerformer);
        timer.setRepeats(false);
        timer.start();


        Background.setBounds(0, 0, height, width);
        add(NameLabel);
    }
}
