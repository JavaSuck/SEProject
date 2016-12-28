package Server.UDPServer;

import Server.CDC.CDC;
import Server.CDC.GameMode;
import Server.TCPServer.TCPServer;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class UDPServer extends Thread {

    private TCPServer TCPServer;
    private CDC cdc;

    public UDPServer(TCPServer TCPServer, CDC cdc) throws IOException {
        this.TCPServer = TCPServer;
        this.cdc = cdc;
    }

    public void run() {
        try {
            startUDPBroadCast();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startUDPBroadCast() throws Exception {
        ArrayList<InetAddress> clientAddresses;
        print("Try to get client table ...");
        do {
            sleep(100);
            clientAddresses = TCPServer.getClientIPTable();
        } while (clientAddresses.size() < GameMode.playerCount - 2);
        print("Get client table successfully");
        while (true) {
            JSONObject updateInfo = cdc.getUpdatingInfo();
            //TODO: check it
            //checkInfo(updateInfo);
            Broadcast broadcast = new Broadcast(clientAddresses, updateInfo);
            broadcast.start();
            sleep(50);
        }
    }

    private void checkInfo(ArrayList<JSONObject> updateInfo) {
        boolean infoCorrect = true;
        do {
            if (!infoCorrect)
                infoCorrect = true;
            for (JSONObject info : updateInfo) {
                infoCorrect &= info.has("players");
                infoCorrect &= info.has("bombs");
                infoCorrect &= info.has("gameState");
            }
        } while (!infoCorrect);
    }

    private void print(String input) {
        String msg = String.format("[UDPServer]: %s", input);
        System.out.println(msg);
    }
}
