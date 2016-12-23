package Server;

import Server.CDC.CDC;
import Server.CDC.Player;
import Server.TCPServer.TCPServer;
import Server.TCPServer.Tool.fakeCDC;
import Server.UDPServer.UDPServer;

import java.io.IOException;

public class Server {

    public Server() throws IOException {
        CDC cdc = new CDC();
        Player p = (Player) cdc.playerController.players.get(1);
        System.out.println(p.coordinate);
        cdc.playerController.walk(1, 2);
        System.out.println(p.coordinate);

        // TODO: Change it to CDC
        fakeCDC fakeCdc = new fakeCDC();
        // TODO: Get connection_limit from gameMode
        TCPServer tcp = new TCPServer(40689, 4, fakeCdc);
        tcp.initTCPServer();

        UDPServer udp = new UDPServer(tcp, cdc);
        udp.start();

    }
}
