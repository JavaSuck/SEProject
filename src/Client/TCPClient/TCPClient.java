package Client.TCPClient;

import Client.Client;
import Server.CDC.GameMode;
import Server.CDC.Stage;
import Server.TCPServer.Action;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import static java.lang.Thread.sleep;

public class TCPClient {

    private Socket connection;
    private BufferedReader receiver;
    private PrintWriter sender;
    private Action actionMap;
    private Client client;
    public int playerId;
    public Stage stage = Stage.LOGIN;

    public TCPClient(Client client) {
        this.client = client;
    }

    public boolean connectServer(InetAddress ipAddress) {
        try {
            String server_ip = ipAddress.getHostAddress();
            SocketAddress sockAddress = new InetSocketAddress(server_ip, GameMode.TCPPort);
            this.connection = new Socket();
            this.connection.connect(sockAddress, 2000);
            this.receiver = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            this.sender = new PrintWriter(connection.getOutputStream());
            this.actionMap = new Action();

            //At first, Server will return JSONObject of connection information.
            JSONObject receive = receiveData();
            assert receive != null;
            print(receive.toString());
            playerId = Integer.parseInt(receive.get("content").toString());

            new Thread(()->{
                try {
                    while (!connection.getKeepAlive()) {
                        print("Disconnect with server.");
                        sleep(1000);
                    }
                }
                catch (SocketException | InterruptedException e){}
            });
        } catch (IOException | NullPointerException | JSONException e) {
            return false;
        }
        return true;
    }

    public void callAction(int actionNo) {
        String moveAction = actionMap.get(actionNo);
        //if action not found.
        if (moveAction == null) {
            print("(WARNING) -> method: callAction > wrong parameter cause action not found.");
            return;
        }
        request(moveAction);
    }

    public void setPlayerName(String playerName){

        assert playerName == null;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "SETNAME");
            jsonObject.put("content", playerName);

            this.sender.println(jsonObject);
            this.sender.flush();

            //wait the data form server.
            JSONObject receive = this.receiveData();
            assert receive != null;
//            if (Boolean.parseBoolean((String) receive.get("content")))
            print("SetPlayerName successfully");
            int stageValue = receive.getInt("stage");
            stage = Stage.getStage(stageValue);
            print("Stage: " + stage);
            client.replaceRoute(stage);
        } catch (JSONException e) {
            printError(e);
        } catch (NullPointerException e){
            print("Server is out of connection.");
        }
    }

    public void getGameState(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "GETSTATE");
            this.sender.println(jsonObject);
            this.sender.flush();
            JSONObject receive = this.receiveData();
            assert receive != null;
            boolean playersAttendance = receive.getBoolean("content");
            print(playersAttendance ? "Players are in attendance" : "Players are not in attendance");
            int stageValue = receive.getInt("stage");
            stage = Stage.getStage(stageValue);
            print("Stage: " + stage);
            client.replaceRoute(stage);
        } catch (JSONException e) {
            printError(e);
        }
    }

    private JSONObject receiveData() {
        try {
            String data = this.receiver.readLine();

            if (data == null)
                return null;

            return new JSONObject(data);
        } catch (JSONException | IOException e) {
            printError(e);
            return null;
        }
    }

    private void request(String input) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "REQUEST");
            jsonObject.put("content", input);

            this.sender.println(jsonObject);
            this.sender.flush();

            //wait the data form server.
            JSONObject receive = this.receiveData();
            assert receive != null;
//            if (Boolean.parseBoolean((String) receive.get("content")))
                print("Request successfully");
        } catch (JSONException e) {
            printError(e);
        }
    }

    public void gameEnd(String[] playerName, int[] playerTime) {
        if (stage == Stage.RESULT) {
            return;
        }
        client.setGameEnd(playerName, playerTime);
        client.replaceRoute(Stage.RESULT);
        stage = Stage.RESULT;
    }

    private void printError(Exception e) {
        String error_string = String.format("(ERROR) Connection:[%s:%s] happend error, -> %s", connection.getInetAddress(), connection.getPort(), e.toString());
        print(error_string);
    }

    private void print(String input) {
        String msg = String.format("[TCPClient]: %s", input);
        System.out.println(msg);
    }
}