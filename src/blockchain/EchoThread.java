package src.blockchain;

import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        System.out.println("Connected to Client");
        InputStream inp = null;
        DataInputStream brinp = null;
        DataOutputStream out = null;
        try {
            inp = socket.getInputStream();
            brinp = new DataInputStream(
                    new BufferedInputStream(inp));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        String line;
        while (true) {
            try {
                line = brinp.readUTF();
                System.out.println(line);
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    System.out.println("Disconnected from client");
                    return;
                } else {
                    String[] parts = line.split(" ");
                    switch (parts[0]) {
                        case "balance":
                            int bal = BlockChain.getbalance(parts[1]);
                            out.writeUTF("Balance = $" + Integer.toString(bal));
                            break;
                        case "transaction":
                            String sender = parts[1];
                            String receiver = parts[2];
                            int amount = Integer.parseInt(parts[3]);
                            boolean res = BlockChain.transact(sender, receiver, amount);
                            if (!res)
                                out.writeUTF("aborted due to insufficient funds");
                            else
                                out.writeUTF("transaction successful");
                            break;
                    }
                    //out.writeUTF("break");//line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
