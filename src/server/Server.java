package src.server;

import src.client.*;
import src.ConnectionManager;

import java.net.*;
import java.io.*;

public class Server
{
	private static ServerSocket serverSocket = null;
	private static int port;

	public static void main(String[] args) throws IOException {
		try
		{
			int port = ConnectionManager.getPort();
			if(args.length > 0) {
				port = Integer.parseInt(args[0]);
				ConnectionManager.setPort(port);
			}
			serverSocket = new ServerSocket(port);
			ConnectionManager.initialize();
			System.out.println("Welcome to Lamport Protocol's Demo!!");
			System.out.println("------------------------------------");
			System.out.println("Waiting for the clients....");

			Socket socket = null;
			String data;
			while(true) {
				try {
					socket = serverSocket.accept();
					ConnectionManager.createClientConnection(socket);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			serverSocket.close();
		}
		return;
	}
}
