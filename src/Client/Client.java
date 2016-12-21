package Client;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.DOM.VirtualCharacter;
import Client.SDM.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Client extends JFrame implements KeyListener {


    private int WINDOW_WIDTH = 910;
    private int WINDOW_HEIGHT = 720;
    private BackgroundCanvas backgroundCanvas;
    private VirtualCharacter character;
    private int delay = 20; // milliseconds

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        setBackground(Color.BLUE);
        setForeground(Color.RED);
        setTitle("SEProject");
    }

    public Client() {
        backgroundCanvas = new BackgroundCanvas();
        SDM sdm = new SDM(backgroundCanvas);
        sdm.loadMap();
//        add(backgroundCanvas);

        initUI();

        FixedCanvas fixedCanvas = new FixedCanvas();

        character = new VirtualCharacter("player1.png");
        fixedCanvas.setMyCharacter(character);
        fixedCanvas.setSize(360, 360);
        character.stop();

        Sidebar sidebar = new Sidebar();
//        JButton btn = new JButton("123");
//
//        sidebar.add(btn);
        JLayeredPane content = new JLayeredPane();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(720, 720));
        backgroundCanvas.setLocation(0, 0);
//        backgroundCanvas.setPreferredSize(new Dimension(816,816));

        content.add(backgroundCanvas);
        backgroundCanvas.setBounds(0, 0, 816, 816);
        content.add(fixedCanvas);
        fixedCanvas.setBounds(0, 0, 720, 720);
        content.moveToFront(fixedCanvas);


        add(BorderLayout.EAST, sidebar);
        add(BorderLayout.CENTER, content);


        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                character.updateAnimation();
                revalidate();
                repaint();
            }
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                character.walk(1);
                backgroundCanvas.moveCanvas(48,0);
                break;
            case KeyEvent.VK_RIGHT:
                character.walk(2);
                backgroundCanvas.moveCanvas(-48,0);
                break;
            case KeyEvent.VK_UP:
                character.walk(3);
                backgroundCanvas.moveCanvas(0,48);
                break;
            case KeyEvent.VK_DOWN:
                backgroundCanvas.moveCanvas(0,-48);
                character.walk(0);
                break;
            case KeyEvent.VK_SPACE:
                character.stop();
                break;
            default:
                break;
        }
    }


}
