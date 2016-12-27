package Client.UIComponents;

import java.awt.*;

import javax.swing.*;

import Client.DOM.VirtualCharacter;

public class FixedCanvas extends JPanel {
    private final int width = 720;
    private final int height = 720;
    private VirtualCharacter myCharacter;

    public FixedCanvas() {
//        setBackground(Color.BLUE);
    }

    public void setMyCharacter(VirtualCharacter character) {
        myCharacter = character;
//        myCharacter.setLocation(336, 336);
//        add(myCharacter);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.drawImage(myCharacter.getAnimationFrame(), 336, 336, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
