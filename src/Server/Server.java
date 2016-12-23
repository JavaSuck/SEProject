package Server;

import Server.CDC.CDC;
import Server.UDPServer.UDPServer;

public class Server {

    public Server() {
        //TCPServer TCP = new TCPServer();
        UDPServer UDP = new UDPServer();
        CDC CDC = new CDC();

        UDP.start();
    }
}
