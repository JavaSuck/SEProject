import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import Client.DOM.VirtualCharacter;

public class GameApp extends JFrame implements KeyListener {
    private int WINDOW_WIDTH = 920;
    private int WINDOW_HEIGHT = 720;
    JPanel panel;

    private VirtualCharacter character;
    private int delay = 20; // milliseconds

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setBackground(Color.WHITE);
        setForeground(Color.black);
        setTitle("SEProject");
    }

    public GameApp() {
        initUI();

        FixedCanvas fixedCanvas = new FixedCanvas();

        character = new VirtualCharacter("player1.png");
        fixedCanvas.setMyCharacter(character);
        fixedCanvas.setSize(720, 720);
        character.stop();

        Sidebar sidebar = new Sidebar();
//        JButton btn = new JButton("123");
//
//        sidebar.add(btn);

        add(BorderLayout.EAST, sidebar);
        add(BorderLayout.CENTER, fixedCanvas);


        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                character.updateAnimation();
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
                break;
            case KeyEvent.VK_RIGHT:
                character.walk(2);
                break;
            case KeyEvent.VK_UP:
                character.walk(3);
                break;
            case KeyEvent.VK_DOWN:
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