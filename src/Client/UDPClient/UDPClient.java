package Client.UDPClient;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.DOM.DOM;
import Server.CDC.Direction;
import Server.CDC.GameMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.*;

@SuppressWarnings("InfiniteLoopStatement")
public class UDPClient extends Thread {

    private DOM dom;
    private BackgroundCanvas backgroundCanvas;

    public UDPClient(DOM dom, BackgroundCanvas backgroundCanvas) {
        this.dom = dom;
        this.backgroundCanvas = backgroundCanvas;
    }

    public void run() {
        try {
            receivePacket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receivePacket() throws IOException {
        final int SIZE = 99999999;
        byte buffer[] = new byte[SIZE];
        print("Start receive packet");
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                DatagramSocket socket = new DatagramSocket(GameMode.UDPPort);
                socket.receive(packet);
                if (!packet.getAddress().equals(InetAddress.getByName(GameMode.serverAddress))) {
                    throw new IOException("Received packet from an unknown source");
                }
                String receiveMessage = new String(buffer, 0, packet.getLength());
                JSONArray messages = new JSONArray(receiveMessage);
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject message = new JSONObject(messages.get(i).toString());
                    int playerId = (int) ((JSONArray) message.get("playerId")).get(0);
                    int nextCoordinateX = (int) ((JSONArray) message.get("nextCoordinateX")).get(0);
                    int nextCoordinateY = (int) ((JSONArray) message.get("nextCoordinateY")).get(0);
                    Point coordinate = new Point(nextCoordinateX, nextCoordinateY);
                    int deadTime = (int) ((JSONArray) message.get("deadTime")).get(0);
                    int usedBomb = (int) ((JSONArray) message.get("usedBomb")).get(0);
                     boolean isCharacterSync = (boolean) ((JSONArray) message.get("isCharacterSync")).get(0);
                    int directionValue = (int) ((JSONArray) message.get("direction")).get(0);
                    Direction direction = Direction.getDirection(directionValue);
                    print("Get message, id = " + playerId + ", coordinate = " + coordinate + ", direction = " + direction + ", isCharacterSync = " + isCharacterSync);
                    dom.updateVirtualCharacter(playerId, direction, nextCoordinateX, nextCoordinateY, isCharacterSync);
                    backgroundCanvas.update(coordinate);
                }
                socket.close();
                sleep(50); // don't modify this
            } catch (InterruptedIOException e) {
                print("Timeout");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void print(String input) {
        String msg = String.format("[UDPClient]: %s", input);
        System.out.println(msg);
    }
}
