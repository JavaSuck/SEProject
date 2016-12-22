package Server.TCPServer.Tool;

import java.net.InetAddress;
import java.util.HashMap;


public class fakeCDC {

	HashMap<Integer, InetAddress> ipTable;

	public fakeCDC() {
		ipTable = new HashMap<Integer, InetAddress>();
	}

	public void addVirtualCharacter(int id, InetAddress addr){
		ipTable.put(id, addr);
		InetAddress address = ipTable.get(id);
		System.out.println("clientToken"+id+" ,"+address);

	}

	public void removeVirtualCharacter(int id){
		ipTable.remove(id);
	}

	public void addBomb(int id){
		System.out.println(id+" -> Call getItem");
	}

	public void updateDirection(int id, int moveCode){
		System.out.println(id+" -> Call updataDirection");
	}


	public void getUpdatingThread(){

	}

	public void getUpdatingInfo(){

	}
}
