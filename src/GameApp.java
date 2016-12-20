import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import DOM.VirtualCharacter;

public class GameApp extends JFrame implements KeyListener {
    private int WINDOW_WIDTH = 1000;
    private int WINDOW_HEIGHT = 1000;
    JPanel panel;

    private VirtualCharacter role1 = new VirtualCharacter("sprite.png");

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("UML Editor");
    }

    public GameApp() {
        initUI();
        setBackground(Color.black);
        setForeground(Color.black);
//        role1.loadSprite("sprite.png");
        role1.stop();
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponents(g);
                g.drawImage(role1.getAnimationFrame(), 384, 416, null);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(800, 800);
            }
        };
        add(panel);
        int delay = 12; // milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                role1.updateAnimation();
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
                role1.walk(0);
                break;
            case KeyEvent.VK_RIGHT:
                role1.walk(1);
                break;
            case KeyEvent.VK_UP:
                role1.walk(3);
                break;
            case KeyEvent.VK_DOWN:
                role1.walk(2);
                break;
            case KeyEvent.VK_SPACE:
                role1.stop();
                break;
            default:
                break;
        }
    }
}