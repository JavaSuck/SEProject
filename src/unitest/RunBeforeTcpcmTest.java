package unitest;
import java.io.IOException;

import Server.TCPServer.TCPServer;
;


public class RunBeforeTcpcmTest {
	public static void main(String args[]){
		try{
			TCPServer server = new TCPServer(40689, 4);
			server.initTCPServer();
		}
		catch (IOException e) {
			// TODO: handle exception
		}

	}
}
