package Server.TCPServer.Tool;

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

    protected Socket connection;
    protected ArrayList<InetAddress> connectionList;
    
    protected int clientToken;
    protected BufferedReader reciever;
    protected PrintWriter sender;
    protected Instruction instructionMap;
    protected TokenRing tokenRing;
    protected fakeCDC cdc;
    

    public ServerThread(Socket connection, ArrayList<InetAddress> connectionList, int clientToken, TokenRing tokenRing, fakeCDC cdc){

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

    protected void listen(){

        JSONObject request = recieve_data();
        
        while(request != null)
        {
            deal(request);
            request = recieve_data();
            
        }

        connection_close();


    }

    protected JSONObject recieve_data(){
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

//    protected void response(String input){
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

    protected void response(JSONObject jsonObject){

        this.sender.println(jsonObject);
        this.sender.flush();
    }

    protected boolean deal(JSONObject request){

        String type = null;
        String content = null;

        try {
	        type = (String)request.get("type");
	        System.out.println("type:"+type);
	        if( type.compareTo("REQUEST") == 0 ) {
	        	
	            content = (String)request.get("content");
		        int instNumber = instructionMap.index(content);
	
		        if(instNumber>0 && instNumber<4){
		        	cdc.updateDirection(clientToken, instNumber);
		        }
		        else if(instNumber == 4){
		        	cdc.addBomb(clientToken);
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


    protected void print(String instruction){
        String get_data = String.format("[%s:%d]: [REQUEST] \t %s", connection.getInetAddress(), connection.getPort(), instruction );
        System.out.println(get_data);

    }

    protected void connection_close(){
    	
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

    protected void removeConnectionList(){

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

    protected void error_handle(Exception e) {
        String error_string = String.format("[%s:%s] \t happend error, => %s", connection.getInetAddress(), connection.getPort(), e.toString());
        System.out.println(error_string);
    }


}