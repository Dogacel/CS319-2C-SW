package SevenWonders.Network;

public interface INetworkListener {
    /**
     * Called when listener receives a message from a connection.
     * @param message Incoming message
     * @param sender ConnectionHandler that received the message
     */
    public void receiveMessage(String message, ConnectionHandler sender);

    /**
     * Called when connection is closed.
     * @param connection Closed connectionHandler
     */
    public void onDisconnect(ConnectionHandler connection);
}
