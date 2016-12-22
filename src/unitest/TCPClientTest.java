package unitest;

import Client.TCPClient.TCPClient;

import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * Created by lucienlo on 2016/12/9.
 */
public class TCPClientTest {

    private TCPClient _client;
    private int _port;
    private String _correctIP;


    @org.junit.Before
    public void setUp() throws Exception {

        _correctIP = "localhost";
        _port = 40689;
        _client = new TCPClient(_port);

    }

    @org.junit.After
    public void tearDown() throws Exception {

        _client = null;
    }


    @org.junit.Test
    public void connectServer() throws Exception {

        String[] ip={
            _correctIP,
            "8.8.8.8",
            "192.168.0.1",
            "localhost",
            "abcdefg",
            "'1=1--"
        };


        for(String currentIP: ip) {

            InetAddress ipAddr=null;

            try {
                ipAddr = InetAddress.getByName(currentIP);
            }
            catch (Exception e){continue;}

            if(currentIP.compareTo(_correctIP) == 0) {
                assertTrue(_client.connectServer(ipAddr));
                _client = null;
                _client = new TCPClient(_port);
            }
            else{
                System.out.println(currentIP);
                assertFalse(_client.connectServer(ipAddr));
            }
        }
//        InetAddress ipAddr = InetAddress.getByName(_correctIP);
//        assertTrue(_client.connectServer(ipAddr));

        _client = null;
        _client = new TCPClient(_port);



    }

    @org.junit.Test
    public void inputMoves() throws Exception {

        InetAddress ipAddr = InetAddress.getByName(_correctIP);
        _client.connectServer(ipAddr);


        //TURNEAST
        _client.inputMoves(0);

        //TURNSOUTH
        _client.inputMoves(1);

        //TURNNORTH
        _client.inputMoves(2);

        //TURNWEST
        _client.inputMoves(3);

        //GET
        _client.inputMoves(4);

        //following is error test, due to the inputMove is not mapping to exist instruction.
        _client.inputMoves(5);
        _client.inputMoves(6);
        _client.inputMoves(7);

        _client = null;
        _client = new TCPClient(_port);

    }
//
//    @org.junit.Test
//    public void recieve_data() throws Exception {
//
//    }
//
//    @org.junit.Test
//    public void request() throws Exception {
//
//    }
//
//    @org.junit.Test
//    public void error_handle() throws Exception {
//
//    }
//
//    @org.junit.Test
//    public void print() throws Exception {
//
//    }

}