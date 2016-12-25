package Client.Scene;

import java.awt.*;

import javax.lang.model.element.Name;
import javax.swing.*;

import Client.ImagePanel.ImagePanel;

/**
 * Created by DMOON on 2016/12/23.
 */
public class Login extends JPanel {

    private int height = 920;
    private int width = 720;


    public Login() {
        setSize(1000, 1000);
        setLayout(null);

        ImagePanel Background = new ImagePanel("login_bg.jpg", height, width);
        JLabel NameLabel = new JLabel("暱稱");
        NameLabel.setFont(new Font("Serif", Font.PLAIN, 24));
        NameLabel.setForeground(Color.WHITE);
        JTextField NameField = new JTextField();
        JButton BtnLogin = new JButton("進入遊戲");

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
