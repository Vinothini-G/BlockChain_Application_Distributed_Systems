package src.client;

import src.ConnectionManager;
import java.net.*;
import java.io.*;

public class ClientConnection extends Thread
{
	private String clientID;
	private Socket socket;
        private DataInputStream inputStream = null;
        private DataOutputStream outputStream = null;

	public ClientConnection(String clientId, Socket socket) {
		this.clientID = clientId;
		this.socket = socket;
		try {
			inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			outputStream = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}

	public void run() {
		System.out.println("Connection Established to "+clientID);
		String data;
		while(true) {
			try {
				data = inputStream.readUTF();
				System.out.println(clientID+" Sent: "+data);
				/* testing code */
				if(data.equals("broadcast"))
					ConnectionManager.broadcast("Broadcast Message!!", clientID);
				else if(data.equals("balance")) {
					String bal = ConnectionManager.getBalance(clientID);
					write(bal);
				}

				//process data: 1-broadcast msg, 2- blockchain call
				//case-1
				//ConnectionManager.broadcast(data, this.clientID);
				//case-2
				//Blockchain get balance
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void write(String writeData) {
		try
		{
			System.out.println(clientID+":  Writing data -> "+writeData);
			outputStream.writeUTF(writeData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
