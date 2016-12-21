package Client.UDPClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPClient extends Thread {

    int port = 5566;

    public void run() {
        try {
            initUPDServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUPDServer() throws Exception {
        final int SIZE = 1024;
        byte buffer[] = new byte[SIZE];
        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(port);
            socket.receive(packet);
            String receiveMessage = new String(buffer, 0, packet.getLength());
            JSONArray messages = new JSONArray(receiveMessage);
            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = new JSONObject(messages.get(i).toString());
                Object command = ((JSONArray) message.get("Command")).get(0);
                /*if (command.equals("ADD")) {
                    Client.BackgroundCanvas.BackgroundCanvas.DOM.addVirtualCharacter(message.get("Character"));
                    Client.BackgroundCanvas.BackgroundCanvas.DOM.addItem(message.get("Item"));
                } else if (command.equals("UPDATE")) {
                    Client.BackgroundCanvas.BackgroundCanvas.DOM.updateVirtualCharacter(message.get("Character"));
                    Client.BackgroundCanvas.BackgroundCanvas.DOM.updateItem(message.get("Item"));
                }*/
            }
            socket.close();
            sleep(200);
        }
    }
}
