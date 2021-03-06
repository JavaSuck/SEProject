package Server.TCPServer;

import Client.Client;
import Client.TCPClient.TCPClient;
import Server.CDC.CDC;
import Server.CDC.GameMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by lucienlo on 2016/12/9.
 */
public class TCPServerTest {

    private TCPServer _server;
    private String _serverName = "localhost";
    private int _port;
    private int _limitedConnections = 4;
    private Client c = new Client();

    private CDC _cdc;

    @Before
    public void setUp() throws Exception {
        _port = 40689;
        _cdc = new CDC();
        _server = new TCPServer(_cdc);
        _server.initTCPServer();

    }

    @After
    public void tearDown() throws Exception {

        _server = null;

    }

//    @Test
//    public void initTCPServer() throws Exception {
//
//        _server.initTCPServer();
//
//        _server = null;
//        _server = new TCPServer(_port, _limitedConnections);
//
//
//    }

    @Test
    public void getClientIPTable() throws Exception{
        getClientIPTable_noConnection();
        getClientIPTable_inValidConnection();
        getClientIPTable_OutofValidConnection();
    }



    private void getClientIPTable_noConnection() throws Exception {

        //if no connection.
        ArrayList<InetAddress> connectionList = _server.getClientIPTable();
        assertEquals(0 ,connectionList.size());

        for(InetAddress ip: connectionList)
            System.out.println(ip.toString());

    }

    private void getClientIPTable_inValidConnection() throws Exception {

        //if has valid number connection

        int ConnectionNumber = GameMode.playerCount;

        for(int i=0; i<ConnectionNumber; i++){
            TCPClient client = new TCPClient(c);
            InetAddress ipAddr = InetAddress.getByName(_serverName);
            client.connectServer(ipAddr);
        }

        ArrayList<InetAddress> connectionList = _server.getClientIPTable();

        assertEquals(ConnectionNumber ,connectionList.size());

        for(InetAddress ip: connectionList)
            System.out.println(ip.toString());



    }

    private void getClientIPTable_OutofValidConnection() throws Exception {

        //if has valid number connection

        int AddConnectionNumber = 5;


        for(int i=0; i<AddConnectionNumber; i++){
            TCPClient client = new TCPClient(c);
            InetAddress ipAddr = InetAddress.getByName(_serverName);
            client.connectServer(ipAddr);
        }

        ArrayList<InetAddress> connectionList = _server.getClientIPTable();

        for(InetAddress ip: connectionList)
            System.out.println(ip);

        for(InetAddress ip: connectionList)
            System.out.println(ip.toString());

    }

//    @Test
//    public void create_thread() throws Exception {
//
//    }
//
//    @Test
//    public void print_connection_info() throws Exception {
//
//    }

}