package Client.UIComponents;

import java.awt.*;

import javax.swing.*;

import Client.AvatarBox.AvatarBox;
import Client.Client;

public class Sidebar extends JPanel {

    private final int width = 165;
    private final int height = 720;

    public Sidebar() {
        setBackground(new Color(35, 105, 186));
//        setBackground(Color.WHITE);
        AvatarBox avatarbox1 = new AvatarBox("YPC", "p1_face.png");
        AvatarBox avatarbox2 = new AvatarBox("YPC", "p1_face.png");
        AvatarBox avatarbox3 = new AvatarBox("YPC", "p1_face.png");
        AvatarBox avatarbox4 = new AvatarBox("YPC", "p1_face.png");

        add(avatarbox1);
        add(avatarbox2);
        add(avatarbox3);
        add(avatarbox4);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
