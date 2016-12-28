package Client;

import Client.Scene.Game;
import Client.Scene.Login;
import Client.TCPClient.TCPClient;
import Server.CDC.GameMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;


public class Client extends JFrame implements KeyListener {

    private int WINDOW_WIDTH = 900;
    private int WINDOW_HEIGHT = 720;
    private HashMap<String, JPanel> scenes = new HashMap<>();
    private Login login;
    private Game game;

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Ice Fire Master YPC");
        setPreferredSize(new Dimension(WINDOW_WIDTH + 2, WINDOW_HEIGHT ));
    }

    public Client() {
        TCPClient tcp = new TCPClient();
        try {
            tcp.connectServer(InetAddress.getByName(GameMode.serverAddress));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        login = new Login(tcp);
        game = new Game(tcp);

        initUI();
        replaceRoute("GAME");

        ActionListener taskPerformer = evt -> {
            revalidate();
            repaint();
        };
        Timer timer = new Timer(15, taskPerformer);
        timer.setRepeats(true);
        timer.start();

        // key handler
        addKeyListener(this);
        setFocusable(true);
    }

    public void replaceRoute(String routeName) {
        // router navigator according to gameStatus
        // TODO: Read from GameState.Stage
        //removeAll();
        switch (routeName) {
            case "LOGIN":
                add(login, BorderLayout.CENTER);
                break;
            case "LOADING":
                break;
            case "GAME":
                game.initGame();
                game.requestFocus();
                add(game, BorderLayout.CENTER);
                break;
            case "RESULT":
                break;
        }
        repaint();
//        removeKeyListener(this);
//        setFocusable(false);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //        System.out.println("keyReleased");
        game.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //        System.out.println("keyTyped");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // switch gameMode
        game.keyPressed(e);
    }
}
