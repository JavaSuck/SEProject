package Client;

import Client.Scene.Game;
import Client.Scene.Loading;
import Client.Scene.Login;
import Client.Scene.Result;
import Client.TCPClient.TCPClient;
import Server.CDC.GameMode;
import Server.CDC.Stage;

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
    private JPanel currentScene = null;
    private Login login;
    private Loading loading;
    private Game game;
    private TCPClient tcp;

    private void initUI() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Ice Fire Master YPC");
        setPreferredSize(new Dimension(WINDOW_WIDTH + 2, WINDOW_HEIGHT));
    }

    public Client() {
        tcp = new TCPClient(this);
        try {
            tcp.connectServer(InetAddress.getByName(GameMode.serverAddress));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        login = new Login(tcp);
        game = new Game(tcp);

        initUI();

        replaceRoute(Stage.LOGIN);

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

    public void replaceRoute(Stage stage) {
        // router navigator according to gameStatus
        System.out.println("[Client]: Replace current scene.");

        if (currentScene != null) {
            remove(currentScene);
        }

        switch (stage) {
            case LOGIN:
//                scenes.put("LOGIN", login);
                currentScene = login;
                add(login, BorderLayout.CENTER);
                break;
            case LOADING:
                loading = new Loading(tcp);
                currentScene = loading;
//                scenes.put("LOADING", loading);
                add(loading, BorderLayout.CENTER);
                break;
            case GAME:
                game.initGame();
                game.requestFocus();
                currentScene = game;
                add(game, BorderLayout.CENTER);
                break;
            case RESULT:
                Result result = new Result();
                currentScene = result;
                add(result, BorderLayout.CENTER);
                break;
        }
        repaint();
//        removeKeyListener(this);
//        setFocusable(false);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
    }
}
