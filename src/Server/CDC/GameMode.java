package Server.CDC;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GameMode {
    // TODO: set it as static ip of one computer
    public static InetAddress serverAddress;
    public static final int playerCount = 4;
    public static final int personalMaxBomb = 4;
    public static final int bombExpireTime = 5;
    public static final double walkSpeed = 0.5;

    public GameMode() {
        try {
            serverAddress = InetAddress.getByName("140.115.51.94");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
