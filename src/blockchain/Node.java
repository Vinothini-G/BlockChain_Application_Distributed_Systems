package src.blockchain;

public class Node {
    String sender;
    String receiver;
    int amount;
    String previousPointer;
    Node(String sender,String receiver, int amount, String last){
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        previousPointer = last;
    }
    Node(String sender,String receiver, int amount){
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }
    public void setPointer(String pointer){
        previousPointer = pointer;
    }
}
