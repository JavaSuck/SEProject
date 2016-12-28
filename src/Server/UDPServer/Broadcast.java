package Server.UDPServer;

import Server.CDC.GameMode;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Broadcast extends Thread {
    private ArrayList<InetAddress> clientAddresses;
    private JSONObject info;

    Broadcast(ArrayList<InetAddress> clientAddresses, JSONObject info) {
        this.clientAddresses = clientAddresses;
        this.info = info;
    }

    public void run() {
        try {
            if (clientAddresses.size() == GameMode.playerCount - 3) {
                DatagramSocket socket = new DatagramSocket();
                String message = info.toString();
                byte buffer[] = message.getBytes();
                for (InetAddress clientAddress : clientAddresses) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientAddress, GameMode.UDPPort);
                    socket.send(packet);
                }
                socket.close();
            } else {
                System.out.println("[UDPServer]: Numbers of clients is not correct.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}