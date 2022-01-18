package src.blockchain;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    //initialize socket and input stream
    static final int PORT = 1978;

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        BlockChain bc = new BlockChain();

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();

        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            new EchoThread(socket).start();
        }
    }
}
