package Server;

import Server.CDC.CDC;
import Server.CDC.Direction;
import Server.CDC.GameMode;
import Server.CDC.Player;
import Server.TCPServer.TCPServer;
import Server.UDPServer.UDPServer;

import java.io.IOException;
import java.net.InetAddress;

public class Server {

    public Server() throws IOException {
        GameMode.serverAddress = InetAddress.getLocalHost();

        CDC cdc = new CDC();
        Player p = cdc.playerController.players.get(1);
        System.out.println(p.coordinate);
        cdc.playerController.walk(1, Direction.DOWN);
        System.out.println(p.coordinate);

        // TODO: Get connection_limit from gameMode
        TCPServer tcp = new TCPServer(40689, cdc);

        tcp.initTCPServer();

        UDPServer udp = new UDPServer(tcp, cdc);
        udp.start();

    }
}
