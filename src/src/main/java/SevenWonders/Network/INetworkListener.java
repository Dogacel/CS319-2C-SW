package SevenWonders.Network;

public interface INetworkListener {
    public void receiveMessage(String message, ConnectionHandler sender);
    public void onDisconnect(ConnectionHandler connection);
}
