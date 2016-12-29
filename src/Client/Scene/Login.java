package Client.Scene;

import Client.Client;
import Client.ImagePanel.ImagePanel;
import Client.TCPClient.TCPClient;

import javax.swing.*;

import java.awt.*;

public class Login extends JPanel {

    private int height = 920;
    private int width = 720;
    private TCPClient tcp;

    public Login(TCPClient tcp, Client client) {
        this.tcp = tcp;
        setSize(1000, 1000);
        setLayout(null);

        ImagePanel Background = new ImagePanel("login_bg.jpg", height, width);
        JLabel NameLabel = new JLabel("暱稱");
        NameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        NameLabel.setForeground(Color.WHITE);
        JTextField NameField = new JTextField();
        JButton BtnLogin = new JButton("進入遊戲");

        BtnLogin.addActionListener(e -> {
            if (NameField.getText() != null) {
                tcp.setPlayerName(NameField.getText());
                client.replaceRoute("LOADING");
            } else {
                //JOptionPane.showMessageDialog("Eggs are not supposed to be green.")
            }
        });

        Background.setBounds(0, 0, height, width);
        NameLabel.setBounds(width / 2, 440, 60, 30);
        NameField.setBounds(width / 2 + 100 / 2, 440, 120, 30);
        BtnLogin.setBounds((width + 80) / 2, 500, 80, 40);

        add(NameLabel);
        add(NameField);
        add(BtnLogin);
        add(Background);
    }

}
