package src;

import src.blockchain.*;
import src.client.ClientConnection;
import java.util.Hashtable;
import java.net.*;

public class ConnectionManager
{
	private static int clientCounter = 1;
	private static int serverPort = 2710;
	private static int bcServerPort = 1978;
	private static Hashtable<String, ClientConnection> clientIDConnMap = new Hashtable();
	private static BlockChainClient bcClient;

	public static void initialize() {
		try {
			bcClient = new BlockChainClient("169.231.20.154", bcServerPort);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String getBalance(String clientID) {
		return bcClient.handleGetRequest("balance "+clientID); //balance query: "balance <client-id>"
	}

	public static void createClientConnection(Socket socket)
	{
		try
		{
			String clientID = "Client" + clientCounter;
			clientCounter++;

			ClientConnection connection = new ClientConnection(clientID, socket);
			connection.start();

			clientIDConnMap.put(clientID, connection);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	//Send to all clients except sender-client
	public static void broadcast(String data, String srcClient) {
		try
		{
			for(String cid : clientIDConnMap.keySet()) {
				if(cid != srcClient) {
					System.out.println("Broadcasting to: "+cid);
					ClientConnection conn = clientIDConnMap.get(cid);
					conn.write(data);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static int getPort() {
		return serverPort;
	}

	public static int getBCServerPort() {
		return bcServerPort;
	}

	public static void setPort(int port) {
		serverPort = port;
	}
}
