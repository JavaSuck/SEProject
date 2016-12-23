package unitest;
import java.io.IOException;

import Server.TCPServer.TCPServer;
import Server.TCPServer.Tool.fakeCDC;
;


public class RunBeforeTcpcmTest {
	public static void main(String args[]){
		try{
			fakeCDC cdc = new fakeCDC();
			TCPServer server = new TCPServer(40689, 4, cdc);
			server.initTCPServer();
		}
		catch (IOException e) {
			// TODO: handle exception
		}

	}
}
