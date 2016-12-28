package Server.TCPServer;

import Server.CDC.CDC;
import Server.CDC.GameMode;
import org.json.JSONException;
import org.json.JSONObject;

import Server.TCPServer.Tool.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

@SuppressWarnings("InfiniteLoopStatement")
public class TCPServer {

    private ServerSocket server;
    private TokenRing tokenRing;
    private ArrayList<InetAddress> connectionList;
    private CDC cdc;

    public TCPServer(CDC cdc) throws IOException {
        this.server = new ServerSocket(GameMode.TCPPort);
        this.tokenRing = new TokenRing(GameMode.playerCount);
        this.connectionList = new ArrayList<>();
        this.cdc = cdc;
        System.out.println("[TCPServer]: Server is running");
    }

    public void initTCPServer() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket connection = server.accept();
                    createThread(connection);
                } catch (IOException e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public ArrayList<InetAddress> getClientIPTable() {
        return connectionList;
    }

    private void createThread(Socket connection) {
        int noToken = -1;
        int clientToken = tokenRing.getToken();
        if (clientToken == noToken) {
            try {
                PrintWriter sender = new PrintWriter(connection.getOutputStream());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "CONNECT");
                jsonObject.put("content", Boolean.toString(false));

                sender.println(jsonObject);
                sender.flush();

                connection.close();

            } catch (IOException | JSONException ignored) {
            }
            return;
        }

        cdc.addVirtualCharacter(clientToken, connection.getInetAddress());
        connectionList.add(connection.getInetAddress());
        new Thread(new ServerThread(connection, connectionList, clientToken, tokenRing, cdc)).start();

        printConnectionInfo(connection);
    }

    private void printConnectionInfo(Socket connection) {
        String login_info = String.format("[%s:%s]: [INFO] Connection Create!", connection.getInetAddress(), connection.getPort());
        System.out.println(login_info);
    }
}

/**
 * Deal request for single Connection
 */
