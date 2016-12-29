package Client.UIComponents;

import Client.AvatarBox.AvatarBox;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {

    private final int width = 165;
    private final int height = 720;
    private int updateCount = 0;

    public Sidebar() {
        setBackground(new Color(35, 105, 186));
    }

    public void updateAvatarBox(int id, String name) {
        if (id == updateCount) {
            AvatarBox avatarBox = new AvatarBox(name, "avatar" + id + ".png");
            add(avatarBox);
            ++updateCount;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
