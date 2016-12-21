package Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Sidebar extends JPanel {

    private final int width = 200;
    private final int height = 720;

    public Sidebar() {
        setBackground(Color.BLACK);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

}
