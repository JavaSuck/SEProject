package Client.UIComponents;

import Client.AvatarBox.AvatarBox;

import javax.swing.*;
import java.awt.*;

public class Sidebar extends JPanel {

    private final int width = 165;
    private final int height = 720;
    private int updateCount = 0;
    private AvatarBox[] avatarBoxes;
    private boolean[] isDead;

    public Sidebar() {
        avatarBoxes = new AvatarBox[4];
        isDead = new boolean[4];
        for (int i = 0; i < 4; i++) {
            isDead[i] = false;
        }
        setBackground(new Color(35, 105, 186));
    }

    public void addAvatarBox(int id, String name) {
        if (id == updateCount) {
            avatarBoxes[id] = new AvatarBox(name, "avatar" + id + ".png");
            add(avatarBoxes[id]);
            ++updateCount;
        }
    }

    public void updateAvatarBox(int id) {
        if (!isDead[id]) {
            avatarBoxes[id].updateAvatar("avatar" + id + "_dead.png");
            isDead[id] = true;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
