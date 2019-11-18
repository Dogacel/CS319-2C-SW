package SevenWonders.Network;

public abstract class AbstractConnectionHandler {
    User user;
    INetworkListener listener;

    public User getUser() {
        return user;
    }
    
    abstract void startListening();
    abstract boolean receiveMessage();
    abstract void sendMessage(String message);
    abstract void disconnect();

}
