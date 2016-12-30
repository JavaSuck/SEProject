package Client.UDPClient;

import Client.BackgroundCanvas.BackgroundCanvas;
import Client.DOM.DOM;
import Server.CDC.Direction;
import Server.CDC.GameMode;
import Server.CDC.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

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
        final int SIZE = 100000;
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
                String receiveMessages = new String(buffer, 0, packet.getLength());
                JSONObject messages = new JSONObject(receiveMessages);

                JSONArray players = messages.getJSONArray("players");
                for (int i = 0; i < players.length(); i++) {
                    JSONObject player = players.getJSONObject(i);
                    int playerId = player.getInt("playerId");
                    String name = player.getString("name");
                    int coordinateNextX = player.getInt("coordinateNextX");
                    int coordinateNextY = player.getInt("coordinateNextY");
                    int deadTime = player.getInt("deadTime");
                    int usedBomb = player.getInt("usedBomb");
                    boolean shouldCharacterSync = player.getBoolean("shouldCharacterSync");
                    int directionValue = player.getInt("direction");
                    Direction direction = Direction.getDirection(directionValue);
                    Point coordinateNext = new Point(coordinateNextX, coordinateNextY);
//                        print("Get Player - id = " + playerId + ", coordinateNext = " + coordinateNext + ", direction = " + direction + ", shouldCharacterSync = " + shouldCharacterSync);
                    dom.updateVirtualCharacter(playerId, direction, coordinateNext, shouldCharacterSync, deadTime);
                    dom.setPlayerName(playerId, name);
                    if (i == dom.getLocalPlayerId()) {
                        backgroundCanvas.update(coordinateNext, shouldCharacterSync);
                    }
                }

                JSONArray bombs = messages.getJSONArray("bombs");
                for (int i = 0; i < bombs.length(); i++) {
                    JSONObject bomb = bombs.getJSONObject(i);
                    int bombId = bomb.getInt("bombId");
                    int playerId = bomb.getInt("playerId");
                    int coordinateX = bomb.getInt("coordinateX");
                    int coordinateY = bomb.getInt("coordinateY");
                    int expireTime = bomb.getInt("expireTime");
                    int power = bomb.getInt("power");
                    boolean isExist = bomb.getBoolean("isExist");
                    Point coordinate = new Point(coordinateX, coordinateY);
                    JSONArray explosionRangeArray = bomb.getJSONArray("explosionRange");
                    int[] explosionRange = new int[4];
                    for (int j = 0; j < explosionRangeArray.length(); ++j) {
                        explosionRange[j] = explosionRangeArray.optInt(j);
                    }
//                    print("Get Bomb - id = " + bombId + ", playerId = " + playerId + ", coordinate = " + coordinate + ", expireTime = " + expireTime + ", isExist = " + isExist);
                    dom.updateBomb(bombId, coordinateX, coordinateY, isExist, explosionRange, power);
                }

                JSONObject gameState = messages.getJSONObject("gameState");
                int gameTime = gameState.getInt("gameTime");
                int livedPlayer = gameState.getInt("livedPlayer");
                int stageValue = gameState.getInt("stage");
                Stage stage = Stage.getStage(stageValue);
//                print("Get gameState - gameTime = " + gameTime + ", livedPlayer = " + livedPlayer + ", stage = " + stage);
                if (stage == Stage.RESULT) {
                    dom.gameEnd();
                }

                socket.close();
                sleep(20);
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
