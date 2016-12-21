package unitest;
import java.io.IOException;

import Server.TCPServer.Tcpsm;
;


public class RunBeforeTcpcmTest {
	public static void main(String args[]){
		try{
			Tcpsm server = new Tcpsm(40689, 4);
			server.initTCPServer();
		}
		catch (IOException e) {
			// TODO: handle exception
		}

	}
}
