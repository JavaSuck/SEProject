package Client.UIComponents;

import Client.AvatarBox.AvatarBox;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {

    private final int width = 165;
    private final int height = 720;
    private int updateCount = 0;
    private AvatarBox[] avatarBoxs = new AvatarBox[4];

    public Sidebar() {
        setBackground(new Color(35, 105, 186));
    }

    public void addAvatarBox(int id, String name) {
        if (id == updateCount) {
            avatarBoxs[id] = new AvatarBox(name, "avatar" + id + ".png");
            add(avatarBoxs[id]);
            ++updateCount;
        }
    }

    public void updateAvatarBox(int id) {
        avatarBoxs[id].updateAvatar("avatar" + id + "_dead.png");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
