package src.client;

import src.ConnectionManager;
import java.net.*;
import java.io.*;

public class Client
{
	private Socket socket = null;
	private DataInputStream terminalStream = null;
	private DataOutputStream outputStream = null;

	public Client(String ipAddress, int port) {
		try
		{
			socket = new Socket(ipAddress, port);
			SocketReadProc readSoc = new SocketReadProc(new DataInputStream(socket.getInputStream()));
			readSoc.start();

			terminalStream = new DataInputStream(System.in);
			outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("Connection Established...");
			String data = null;
			while(true) {
				//established connection
				data = terminalStream.readLine();
				System.out.println("Input Data: "+data);
				outputStream.writeUTF(data);
				outputStream.flush();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				socket.close();
				terminalStream.close();
				outputStream.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	class SocketReadProc extends Thread {
		private DataInputStream socketStream = null;

		SocketReadProc(DataInputStream socketStream) {
			this.socketStream = socketStream;
		}

		public void run() {
			try {
				String data = null;
				while(true) {
					data = socketStream.readUTF();
					System.out.println("Socket Read: "+data);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) throws IOException {
		Client client = new Client("127.0.0.1", ConnectionManager.getPort());
	}
}
