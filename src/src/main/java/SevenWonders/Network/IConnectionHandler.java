package SevenWonders.Network;

public interface IConnectionHandler {
    User getUser();
    void startListening();
    boolean receiveMessage();
    void sendMessage(String message);
    void disconnect();
}
