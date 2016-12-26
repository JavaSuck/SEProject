package Client.UDPClient;

import Server.CDC.Direction;
import Server.CDC.GameMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

@SuppressWarnings("InfiniteLoopStatement")
public class UDPClient extends Thread {

    public void run() {
        try {
            receivePacket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receivePacket() throws Exception {
        final int SIZE = 1024;
        byte buffer[] = new byte[SIZE];
        print("Start receive packet");
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(GameMode.UDPPort);
            socket.receive(packet);
            String receiveMessage = new String(buffer, 0, packet.getLength());
            JSONArray messages = new JSONArray(receiveMessage);
            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = new JSONObject(messages.get(i).toString());
                int playerId = (int) ((JSONArray) message.get("playerId")).get(0);
                int coordinateX = (int) ((JSONArray) message.get("coordinateX")).get(0);
                int coordinateY = (int) ((JSONArray) message.get("coordinateY")).get(0);
                Point coordinate = new Point(coordinateX, coordinateY);
                int deadTime = (int) ((JSONArray) message.get("deadTime")).get(0);
                int usedBomb = (int) ((JSONArray) message.get("usedBomb")).get(0);
                boolean isWalk = (boolean) ((JSONArray) message.get("isWalk")).get(0);
                int directionValue = (int) ((JSONArray) message.get("direction")).get(0);
                Direction direction = Direction.getDirection(directionValue);
                print("Get message, id = " + playerId + ", coordinate = " + coordinate + ", direction = " + direction);
            }
            socket.close();
            sleep(200);
        }
    }

    private void print(String input) {
        String msg = String.format("[UDPClient]: %s", input);
        System.out.println(msg);
    }
}
