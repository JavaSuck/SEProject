package Server;

import Server.CDC.CDC;
import Server.CDC.Player;
import Server.UDPServer.UDPServer;

public class Server {

    public Server() {
        //TCPServer tcp = new TCPServer();

        UDPServer udp = new UDPServer();
        udp.start();

        CDC cdc = new CDC();
        Player p = (Player) cdc.playerController.players.get(1);
        System.out.println(p.coordinate);
        cdc.playerController.walk(1, 2);
        System.out.println(p.coordinate);

    }
}
