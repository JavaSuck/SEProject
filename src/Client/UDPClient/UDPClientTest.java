package Client.UDPClient;

import Client.DOM.DOM;
import Server.CDC.GameMode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import static org.junit.Assert.*;

class UDPClientTest {
    private UDPClient UDPClient;
    private DOM dom;

    @Before
    void setUp() {
        UDPClient = new UDPClient(dom);
    }

    @After
    void tearDown() {
        UDPClient = null;
    }

    @Test
    void main() {
        assertNotNull(UDPClient);
    }

    @Test
    void run() {
        assertNotNull(UDPClient);
    }

    @Test
    void initUPDServer() throws Exception {
        final int SIZE = 1024;
        byte buffer[] = new byte[SIZE];
        boolean infoCorrect = true;
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        DatagramSocket socket = new DatagramSocket(GameMode.UDPPort);
        for (int i = 0; i < 10; i++) {
            socket.receive(packet);
            String receiveMessage = new String(buffer, 0, packet.getLength());
            JSONArray messages = new JSONArray(receiveMessage);
            for (int j = 0; j < messages.length(); j++) {
                JSONObject message = new JSONObject(messages.get(j).toString());
                Object command = ((JSONArray) message.get("Command")).get(0);
                if (i == 0)
                    infoCorrect &= command.equals("ADD");
                else
                    infoCorrect &= command.equals("UPDATE");
            }
        }
        socket.close();
        assertTrue(infoCorrect);
    }

    @Test
    void initInvalidUPDServer() throws Exception {
        boolean infoCorrect = true;
        ArrayList<JSONObject> messages = new ArrayList<JSONObject>() {{
            JSONObject info = new JSONObject();
            info.append("Character", "Jason Wu");
            info.append("Item", "Bomb");
            info.append("Command", "UPDATE");
            add(info);
            add(info);
        }};
        for (int i = 0; i < 2; i++) {
            for (JSONObject message : messages) {
                Object command = ((JSONArray) message.get("Command")).get(0);
                if (i == 0)
                    infoCorrect &= command.equals("ADD");
                else
                    infoCorrect &= command.equals("UPDATE");
            }
        }
        assertTrue(!infoCorrect);
    }

}