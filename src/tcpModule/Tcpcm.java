package tcpModule;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Tcpcm {

    protected int port;
    protected Socket connection;

    protected BufferedReader reciever;
    protected PrintWriter sender;
    protected Instruction instructionMap;


    public Tcpcm(int port){

        this.port = port;

    }

    public boolean connectServer(InetAddress ipAddr){

        try {
            String server_ip = ipAddr.getHostAddress().toString();

//            this.connection = new Socket(server_ip, this.port);
            SocketAddress sockAddr = new InetSocketAddress(server_ip, this.port);
            this.connection = new Socket();
            connection.connect(sockAddr, 2000);


            this.reciever = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            this.sender = new PrintWriter(connection.getOutputStream());
            this.instructionMap = new Instruction();


            //At first, Server will return a line of connection information.
            JSONObject recieve = recieve_data();
            String response = (String) recieve.get("content");

            print(response);
        }
        catch (IOException e){
            return false;
        }
        catch (NullPointerException e){
            //connection not found, cause no recieve data.
            return false;
        }
        catch (JSONException e){
            return false;
        }

        return true;
    }


    public void inputMoves(int moveCode){

        String moveInstruction = instructionMap.get(moveCode);

        //if instruction not found.
        if(moveInstruction==null) {
            print("(WARNING) -> method: inputMoves > wrong parameter cause instruction not found.");
            return;
        }

        request(moveInstruction);

    }


    protected JSONObject recieve_data(){
        try{
            String data = this.reciever.readLine();

            if(data==null)
                return null;

            return new JSONObject(data);
        }
        catch(JSONException e){
            error_handle(e);
            return null;
        }
        catch(IOException e){
            error_handle(e);
            return null;

        }
    }

    protected void request(String input){

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("type", "REQUEST");
            jsonObject.put("content", input);

            System.out.println(jsonObject);

            this.sender.println(jsonObject);
            this.sender.flush();

            JSONObject recieve = this.recieve_data();
            String response = (String)recieve.get("content");

            System.out.println(response);

        }
        catch (JSONException e){
            error_handle(e);
        }


    }

    protected void error_handle(Exception e) {
        String error_string = String.format("(ERROR) Connection:[%s:%s] happend error, -> %s", connection.getInetAddress(), connection.getPort(), e.toString());
        print(error_string);
    }


    protected void print(String input){
        String msg = String.format("[TCPCM]: %s", input);
        System.out.println(msg);
    }

}