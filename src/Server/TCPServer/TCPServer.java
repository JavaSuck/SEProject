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

    //outside class

    protected ServerSocket server;
    protected TokenRing tokenRing;
    protected ArrayList<InetAddress> connectionList;
    protected CDC cdc;

    public TCPServer(int port,  CDC cdc) throws IOException {
        this.server = new ServerSocket(port);
        this.tokenRing = new TokenRing(GameMode.playerCount);
        this.connectionList = new ArrayList<InetAddress>();
        this.cdc = cdc;

        System.out.println("Server is running");
    }

    public void initTCPServer() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Socket connection = server.accept();
                        create_thread(connection);
                    } catch (IOException e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                }

            }

        }).start();

    }

    public ArrayList<InetAddress> getClientIPTable() {

        return connectionList;

    }

//    public Socket[] getClientIPTable(){
//    	
//    	int connectionNumber = connectionList.size();
//    	
//    	Socket[] connectionArr = new Socket[connectionNumber];
//    	connectionList.toArray(connectionArr);
//    	
//    	return connectionArr;
//    	
//    }


    protected void create_thread(Socket connection) {

        int noValue = -1;
        int client_token = tokenRing.token_selector();

        if (client_token == noValue) {

            try {

                PrintWriter sender = new PrintWriter(connection.getOutputStream());

                JSONObject jsonObject = new JSONObject();


                jsonObject.put("type", "CONNECT");
                jsonObject.put("content", Boolean.toString(false));


                sender.println(jsonObject);
                sender.flush();

                connection.close();

            } catch (IOException e) {
            } catch (JSONException e) {
            }

            return;
        }

        cdc.addVirtualCharacter(client_token, connection.getInetAddress());

        new Thread(new ServerThread(connection, connectionList, client_token, tokenRing, cdc)).start();

        connectionList.add(connection.getInetAddress());

        print_connection_info(connection);
    }

    protected void print_connection_info(Socket connection) {

        String login_info = String.format("[%s:%s]: [INFO] Connection Create!", connection.getInetAddress(), connection.getPort());
        System.out.println(login_info);

    }
}


/**
 * Deal request for single Connection
 */


