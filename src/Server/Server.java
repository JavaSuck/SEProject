package Server;

import Server.CDC.CDC;
import Server.TCPServer.TCPServer;
import Server.UDPServer.UDPServer;

import java.io.IOException;

public class Server {

    public Server() throws IOException {
        CDC cdc = new CDC();

        TCPServer tcp = new TCPServer(cdc);
        tcp.initTCPServer();

        UDPServer udp = new UDPServer(tcp, cdc);
        udp.start();
    }
}
