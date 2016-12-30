package Server.TCPServer.Tool;


import static java.lang.Thread.sleep;

import Server.CDC.*;

import java.net.SocketException;
import org.json.JSONException;
import org.json.JSONObject;

import Server.CDC.GameState;
import Server.CDC.Stage;
import Server.TCPServer.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable {
    private Socket connection;
    private ArrayList<InetAddress> connectionList;
    private int clientToken;
    private BufferedReader receiver;
    private PrintWriter sender;
    private Action actionMap;
    private TokenRing tokenRing;
    private CDC cdc;

    public ServerThread(Socket connection, ArrayList<InetAddress> connectionList, int clientToken, TokenRing tokenRing, CDC cdc) {
        //Handle yourself connection
        this.connection = connection;
        this.connectionList = connectionList;
        this.clientToken = clientToken;
        this.cdc = cdc;
//        this.cdc.addVirtualCharacter(clientToken, connection.getInetAddress());

        try {
            this.receiver = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            this.sender = new PrintWriter(connection.getOutputStream());
            this.actionMap = new Action();
            this.tokenRing = tokenRing;
        } catch (IOException e) {
            printError(e);
        }

        new Thread(()->{
            try {
                while (connection.getKeepAlive()) {
                    sleep(1000);
                }

                closeConnection();
            }
            catch (SocketException | InterruptedException e){}
        });
    }

    public void run() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "CONNECT");
        jsonObject.put("content", Integer.toString(this.clientToken));
        response(jsonObject);
        listen();
    }

    private void listen() {
        JSONObject request = receiveData();
        while (request != null) {
            deal(request);
            request = receiveData();
        }
        closeConnection();
    }

    private JSONObject receiveData() {
        try {
            String data = this.receiver.readLine();
            if (data == null)
                return null;
            return new JSONObject(data);
        } catch (JSONException e) {
            printError(e);
            return null;
        } catch (IOException e) {
            printError(e);
            return null;
        }
    }

    private void response(JSONObject jsonObject) {

        this.sender.println(jsonObject);
        this.sender.flush();
    }

    private boolean deal(JSONObject request) {
        String type;
        String content = null;
        boolean requestResult = false;

        try {
            type = (String) request.get("type");
            if (type.compareTo("REQUEST") == 0) {
                content = (String) request.get("content");
                print("type:" + type + ", content:" + content);
                int actionNumber = actionMap.index(content);

                switch (actionNumber) {
                    case 0:
                        requestResult = cdc.playerSlip(clientToken, Direction.DOWN);
                        break;
                    case 1:
                        requestResult = cdc.playerSlip(clientToken, Direction.LEFT);
                        break;
                    case 2:
                        requestResult = cdc.playerSlip(clientToken, Direction.RIGHT);
                        break;
                    case 3:
                        requestResult = cdc.playerSlip(clientToken, Direction.UP);
                        break;
                    case 4:
                        requestResult = cdc.addBomb(clientToken);
                        break;
                    default: {
                        System.out.println("Find unknown action");
                    }
                }


            } else if (type.compareTo("SETNAME") == 0) {
                String playerName = (String) request.get("content");
                requestResult = cdc.setPlayerName(clientToken, playerName);
                GameState.stage = Stage.LOADING;
            } else if (type.compareTo("GETSTATE") == 0) {
                requestResult = connectionList.size() == GameMode.UdpPlayerCount;
                if (requestResult)
                    GameState.stage = Stage.GAME;
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "RESPONSE");
            jsonObject.put("content", requestResult);
            jsonObject.put("stage", GameState.stage.getValue());
            response(jsonObject);


        } catch (NullPointerException e) {
            String answer = String.format("[WARING] \t Action: \"%s\" is NOT FOUND", content);
            print(answer);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "RESPONSE");
            jsonObject.put("content", Boolean.toString(false));

            response(jsonObject);
        } catch (JSONException e) {
            printError(e);
        }
        return true;
    }

    private void closeConnection() {
        try {
            removeConnectionList();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "CLOSE");
            jsonObject.put("content", Integer.toString(this.clientToken));
            connection.close();

            tokenRing.removeToken(this.clientToken);

            String info_string = String.format("[%s:%s] \t Connection Closed!", connection.getInetAddress(), connection.getPort());
            System.out.println(info_string);
        } catch (IOException e) {
            printError(e);
        }
    }

    private void removeConnectionList() {
        int index;
        for (index = 0; index < connectionList.size(); index++) {
            String currentIP = connectionList.get(index).toString();
            String connectionIP = connection.getInetAddress().toString();
            if (currentIP.compareTo(connectionIP) == 0)
                break;
        }
        connectionList.remove(index);
        cdc.removeVirtualCharacter(clientToken);
    }

    private void print(String action) {
        String getData = String.format("[%s:%d]: [REQUEST] \t %s", connection.getInetAddress(), connection.getPort(), action);
        System.out.println(getData);
    }

    private void printError(Exception e) {
        String error_string = String.format("[%s:%s] \t happend error, => %s", connection.getInetAddress(), connection.getPort(), e.toString());
        print(error_string);
    }

}