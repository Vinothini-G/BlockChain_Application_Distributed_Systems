package src.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class BlockChain {
    private static List<Node> chain;
    private static final int INITIAL_BALANCE = 10;

    BlockChain() {
        chain = new ArrayList<Node>();
        chain.add(new Node("null", "null", 0, "null"));
    }

    public static int getbalance(String client) {
        int balance = INITIAL_BALANCE;
        for (int i = 1; i < chain.size(); i++) {
            Node curr = chain.get(i);
            if (curr.sender.equalsIgnoreCase(client))
                balance -= curr.amount;
            else if (curr.receiver.equalsIgnoreCase(client))
                balance += curr.amount;
        }
        return balance;
    }

    public static boolean transact(String sender, String receiver, int amount) {
        int balance = getbalance(sender);
        if (amount > balance)
            return false;
        Node nodeToAdd = new Node(sender, receiver, amount);
        Node curr = chain.get(chain.size() - 1);
        String previousPointer = String.join("|", curr.sender, curr.receiver, Integer.toString(curr.amount), curr.previousPointer);
        nodeToAdd.setPointer(previousPointer);
        chain.add(nodeToAdd);
        return true;
    }

    public static String getSHA256Hash(String seed) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                seed.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
