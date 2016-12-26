package Client.Scene;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.DOM.DOM;
import Client.DOM.VirtualCharacter;
import Client.SDM.SDM;
import Client.UIComponents.FixedCanvas;
import Client.UIComponents.Sidebar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener {

    private int WINDOW_WIDTH = 910;
    private int WINDOW_HEIGHT = 720;
    private BackgroundCanvas backgroundCanvas;
    private VirtualCharacter character;
    private int delay = 20; // milliseconds
    private DOM dom;
    private SDM sdm;

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        setForeground(Color.RED);
    }

    public Game() {
        backgroundCanvas = new BackgroundCanvas();
        character = new VirtualCharacter("player1.png");
        dom = new DOM(backgroundCanvas, character);
        sdm = new SDM(backgroundCanvas);
        sdm.loadMap();

        initUI();

        FixedCanvas fixedCanvas = new FixedCanvas();
        fixedCanvas.setMyCharacter(character);
        fixedCanvas.setSize(360, 360);
        character.stop();

        Sidebar sidebar = new Sidebar();
        JLayeredPane content = new JLayeredPane();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(720, 720));
        backgroundCanvas.setLocation(0, 0);
        content.add(backgroundCanvas);
        backgroundCanvas.setBounds(0, 0, 816, 816);
        content.add(fixedCanvas);
        fixedCanvas.setBounds(0, 0, 720, 720);
        content.moveToFront(fixedCanvas);

        add(content, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);

        ActionListener taskPerformer = evt -> {
            character.updateAnimation();
            revalidate();
            repaint();
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(true);
        timer.start();

        // key handler
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //        System.out.println("keyReleased");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //        System.out.println("keyTyped");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        dom.keyPressed(e);
    }

}
