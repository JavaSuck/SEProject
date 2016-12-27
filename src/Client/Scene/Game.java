package Client.Scene;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.Bomb.Bomb;
import Client.DOM.DOM;
import Client.DOM.VirtualCharacter;
import Client.SDM.SDM;
import Client.TCPClient.TCPClient;
import Client.UDPClient.UDPClient;
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
    this.tcp = tcp;
    this.backgroundCanvas = new BackgroundCanvas();
    this.dom = new DOM(tcp, backgroundCanvas);
    this.sdm = new SDM(backgroundCanvas);
    this.sdm.loadMap();
    this.udp = new UDPClient(dom, backgroundCanvas);
    this.udp.start();
    this.localPlayer = dom.localPlayer;

    initUI();

    FixedCanvas fixedCanvas = new FixedCanvas();
    fixedCanvas.setMyCharacter(localPlayer);
    fixedCanvas.setSize(360, 360);
    localPlayer.stop();

    Sidebar sidebar = new Sidebar();
    JLayeredPane content = new JLayeredPane();
    content.setLayout(null);
    content.setPreferredSize(new Dimension(CAMERA_WIDTH, CAMERA_HEIGHT));

    backgroundCanvas.initCanvasPosition();
    backgroundCanvas.initTimer();
    backgroundCanvas.setSize(BackgroundCanvas.CANVAS_WIDTH, BackgroundCanvas.CANVAS_HEIGHT);
    content.add(backgroundCanvas);

    fixedCanvas.setBounds(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
    content.add(fixedCanvas);

    content.moveToFront(fixedCanvas);

    Bomb bomb = new Bomb();
    bomb.setBounds(80, 80, BLOCK_PIXEL, BLOCK_PIXEL);
    backgroundCanvas.add(bomb);

    add(content, BorderLayout.CENTER);
    add(sidebar, BorderLayout.EAST);

    ActionListener taskPerformer = evt -> {
//            DOM.updateAll();
      localPlayer.updateAnimation();
      bomb.updateAnimation();
      revalidate();
//            backgroundCanvas.repaint();
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
