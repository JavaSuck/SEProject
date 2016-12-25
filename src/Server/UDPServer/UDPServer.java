package Server.UDPServer;

import Server.CDC.CDC;
import Server.TCPServer.TCPServer;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class UDPServer extends Thread {

    // TODO: Get this from gameMode
    private int playerCount = 4;
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
        do {
            sleep(200);
            clientAddresses = TCPServer.getClientIPTable();
        } while (clientAddresses.size() < playerCount);
        ArrayList<JSONObject> firstEncodeInfo = getEncodeInfo("ADD");
        Broadcast firstBroadcast = new Broadcast(clientAddresses, firstEncodeInfo);
        firstBroadcast.start();
        while (true) {
            ArrayList<JSONObject> encodeInfo = getEncodeInfo("UPDATE");
            Broadcast broadcast = new Broadcast(clientAddresses, encodeInfo);
            broadcast.start();
            sleep(200);
        }
    }

    private ArrayList<JSONObject> getEncodeInfo(String command) {
        ArrayList<JSONObject> updateInfo = cdc.getUpdatingInfo();
        boolean infoCorrect = true;
        do {
            if (!infoCorrect)
                infoCorrect = true;
            for (JSONObject info : updateInfo) {
                infoCorrect &= info.has("Character");
                infoCorrect &= info.has("Item");
            }
        } while (!infoCorrect);
        ArrayList<JSONObject> encodeInfo = new ArrayList<>();
        for (JSONObject info : updateInfo) {
            Object character = info.get("Character");
            Object item = info.get("Item");
            JSONObject newInfo = new JSONObject();
            newInfo.append("Command", command);
            newInfo.append("Character", character);
            newInfo.append("Item", item);
            encodeInfo.add(newInfo);
        }
        return encodeInfo;
    }
}
