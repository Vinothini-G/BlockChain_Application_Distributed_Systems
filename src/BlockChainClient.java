package src;

import src.ConnectionManager;
import java.net.*;
import java.io.*;

public class BlockChainClient extends Thread
{
	private Socket socket = null;
	private DataInputStream inputStream = null;
	private DataOutputStream outputStream = null;

	public BlockChainClient(String ipAddress, int port) {
		try
		{
			socket = new Socket(ipAddress, port);
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("Established Connection to BlockChain Server...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String handleGetRequest(String data) {
		try
		{
			this.outputStream.writeUTF(data);
			this.outputStream.flush();
			String response = this.inputStream.readUTF();
			//System.out.println("handleGet Response : "+response);
			return response;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
