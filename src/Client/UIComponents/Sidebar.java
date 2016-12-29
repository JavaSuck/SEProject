package Client.UIComponents;

import Client.AvatarBox.AvatarBox;

import javax.swing.*;

import java.awt.*;

public class Sidebar extends JPanel {

    private final int width = 165;
    private final int height = 720;

    public Sidebar() {
        setBackground(new Color(35, 105, 186));
//        setBackground(Color.WHITE);
        AvatarBox avatarBox1 = new AvatarBox("YPC", "avatar0.png");
        AvatarBox avatarBox2 = new AvatarBox("YPC", "avatar1.png");
        AvatarBox avatarBox3 = new AvatarBox("YPC", "avatar2.png");
        AvatarBox avatarBox4 = new AvatarBox("YPC", "avatar3.png");

        add(avatarBox1);
        add(avatarBox2);
        add(avatarBox3);
        add(avatarBox4);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
