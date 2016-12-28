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
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
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
        add(login, BorderLayout.CENTER);
        replaceRoute("login");
//        remove(login);
//        add(game, BorderLayout.CENTER);

        ActionListener taskPerformer = evt -> {
//          character.updateAnimation();
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
//        scenes.each( scene => remove(scene));
//        add(secnes.get(GameStatus.getRoute()));
        remove(login);
//        removeKeyListener(this);
//        setFocusable(false);
        game.initGame();
        game.requestFocus();
        add(game, BorderLayout.CENTER);
        repaint();
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
