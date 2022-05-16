package Server_Package;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

//Written by Amirul Asyraaf Bin Asri B032110030

public class Server_Interface {
	
	public static void main(String[] args) throws IOException {
		Socket socket= new Socket ("localhost",9999);
		DataInputStream InStream=new DataInputStream(socket.getInputStream());
		String message = "";
		while(true)
		{
			message= InStream.readUTF();
			System.out.println(message);
		}
	}
}
