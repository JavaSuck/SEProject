package Server.TCPServer.Tool;

import Server.CDC.CDC;
import Server.CDC.Direction;
import Server.TCPServer.TCPServer;
import org.json.JSONException;
import org.json.JSONObject;
import Server.TCPServer.Instruction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable{

    private Socket connection;
    private ArrayList<InetAddress> connectionList;
    
    private int clientToken;
    private BufferedReader reciever;
    private PrintWriter sender;
    private Instruction instructionMap;
    private TokenRing tokenRing;
    private CDC cdc;
    

    public ServerThread(Socket connection, ArrayList<InetAddress> connectionList, int clientToken, TokenRing tokenRing, CDC cdc){

        //Handle yourself connection
        this.connection = connection;
        this.connectionList = connectionList;
        
        this.clientToken = clientToken;

        this.cdc = cdc;
//        this.cdc.addVirtualCharacter(clientToken, connection.getInetAddress());
        
        try{
            this.reciever = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            this.sender = new PrintWriter(connection.getOutputStream());
            this.instructionMap = new Instruction();
            this.tokenRing = tokenRing;
        }
        catch (IOException e) {
            error_handle(e);
        }
    }

    public void run(){

        //response to client, show the client unique token.
//        String info_msg = String.format("[Server]: [INFO] \t Connect Successfully! \t Your token is %d" , this.clientToken);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "CONNECT");
        jsonObject.put("content", Integer.toString(this.clientToken));

        response(jsonObject);

        listen();
    }

    private void listen(){

        JSONObject request = recieve_data();
        
        while(request != null)
        {
            deal(request);
            request = recieve_data();
            
        }

        connection_close();


    }

    private JSONObject recieve_data(){
        try{
            String data = this.reciever.readLine();
            if(data == null)
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

//    private void response(String input){
//
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//
//            jsonObject.put("type", "RESPONSE");
//            jsonObject.put("content", input);
//
////            System.out.println(jsonObject);
//
//        }
//        catch (JSONException e){
//            error_handle(e);
//        }
//
//        this.sender.println(jsonObject);
//        this.sender.flush();
//    }

    private void response(JSONObject jsonObject){

        this.sender.println(jsonObject);
        this.sender.flush();
    }

    private boolean deal(JSONObject request){

        String type = null;
        String content = null;

        try {
	        type = (String)request.get("type");
	        System.out.println("type:"+type);
	        if( type.compareTo("REQUEST") == 0 ) {
	        	
	            content = (String)request.get("content");
		        int instNumber = instructionMap.index(content);


                switch (instNumber){
                    case 1:
                        cdc.updateDirection(clientToken, Direction.DOWN);
                        break;
                    case 2:
                        cdc.updateDirection(clientToken, Direction.LEFT);
                        break;
                    case 3:
                        cdc.updateDirection(clientToken, Direction.RIGHT);
                        break;
                    case 4:
                        cdc.updateDirection(clientToken, Direction.UP);
                        break;
                    case 5:
                        cdc.addBomb(clientToken);
                        break;
                    default:{
                        System.out.println("Find unknown instruction");
                    }
                }
		        
//		        print(content+", get token is "+instNumber);


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("type", "RESPONSE");
                jsonObject.put("content", Boolean.toString(true));

                response(jsonObject);
	        
	        }

        }catch (NullPointerException e) {

            String answer = String.format("[Server]: [WARING] \t Instruction: \"%s\" is NOT FOUND", content);
            print(answer);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "RESPONSE");
            jsonObject.put("content", Boolean.toString(false));

            response(jsonObject);

        }
        catch (JSONException e){
            error_handle(e);
        }


        return true;



    }


    private void print(String instruction){
        String get_data = String.format("[%s:%d]: [REQUEST] \t %s", connection.getInetAddress(), connection.getPort(), instruction );
        System.out.println(get_data);

    }

    private void connection_close(){
    	
        try{

            removeConnectionList();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", "CLOSE");
            jsonObject.put("content", Integer.toString(this.clientToken));
            connection.close();
            
            tokenRing.token_discard(this.clientToken);

            String info_string = String.format("[%s:%s] \t Connection Closed!", connection.getInetAddress(), connection.getPort());
            System.out.println(info_string);
        }
        catch (IOException e) {
            error_handle(e);
        }

    }

    private void removeConnectionList(){

        int index=0;

        for(index=0; index<connectionList.size(); index++){
            String currentIP = connectionList.get(index).toString();
            String connectionIP = connection.getInetAddress().toString();
            if( currentIP.compareTo(connectionIP) == 0)
                break;
        }

        connectionList.remove(index);
        cdc.removeVirtualCharacter(clientToken);

    }

    private void error_handle(Exception e) {
        String error_string = String.format("[%s:%s] \t happend error, => %s", connection.getInetAddress(), connection.getPort(), e.toString());
        System.out.println(error_string);
    }


}