package Client.Scene;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.DOM.DOM;
import Client.DOM.VirtualCharacter;
import Client.SDM.SDM;
import Client.TCPClient.TCPClient;
import Client.UDPClient.UDPClient;
import Client.UIComponents.FixedCanvas;
import Client.UIComponents.Sidebar;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Game extends JPanel {

    private int WINDOW_WIDTH = 910;
    private int WINDOW_HEIGHT = 720;
    public static int CAMERA_WIDTH = 720;
    public static int CAMERA_HEIGHT = 720;
    public static final int BLOCK_PIXEL = 48;

    private BackgroundCanvas backgroundCanvas;
    private VirtualCharacter localPlayer;
    private int delay = 20; // milliseconds
    private DOM dom;
    private SDM sdm;
    private TCPClient tcp;
    private UDPClient udp;

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public Game(TCPClient tcp) {
        initUI();
        this.tcp = tcp;
    }

    public void initGame() {
        this.backgroundCanvas = new BackgroundCanvas();
        this.dom = new DOM(tcp, backgroundCanvas);
        this.sdm = new SDM(backgroundCanvas);
        this.sdm.loadMap();
        this.udp = new UDPClient(dom, backgroundCanvas);
        this.udp.start();
        this.localPlayer = dom.localPlayer;

        FixedCanvas fixedCanvas = new FixedCanvas();
        fixedCanvas.setMyCharacter(localPlayer);
        fixedCanvas.setSize(360, 360);
        localPlayer.stop();

        Sidebar sidebar = new Sidebar();
        JLayeredPane content = new JLayeredPane();
        content.setLayout(null);
        content.setPreferredSize(new Dimension(CAMERA_WIDTH, CAMERA_HEIGHT));

        dom.setSidebar(sidebar);

        backgroundCanvas.initCanvasPosition();
        backgroundCanvas.initTimer();
        backgroundCanvas.setSize(BackgroundCanvas.CANVAS_WIDTH, BackgroundCanvas.CANVAS_HEIGHT);
        content.add(backgroundCanvas);

        fixedCanvas.setBounds(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        content.add(fixedCanvas);

        content.moveToFront(fixedCanvas);

        add(content, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);

        ActionListener taskPerformer = evt -> {
            dom.updateAllAnimationFrame();
            revalidate();
//            backgroundCanvas.repaint();
            repaint();
        };
        Timer timer = new Timer(delay, taskPerformer);
        timer.setRepeats(true);
        timer.start();

    }

    public void keyReleased(KeyEvent e) {
        //        System.out.println("keyReleased");
        try {
            dom.keyReleased(e);
        }catch (NullPointerException exp){}
    }

    public void keyTyped(KeyEvent e) {
        //        System.out.println("keyTyped");
    }

    public void keyPressed(KeyEvent e) {
        try {
            dom.keyPressed(e);
        }catch (NullPointerException exp){}
    }
}
