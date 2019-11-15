package SevenWonders.Network;

interface INetworkListener {
    /**
     * Called when listener receives a message from a connection.
     * @param message Incoming message
     * @param sender ConnectionHandler that received the message
     */
    void receiveMessage(String message, AbstractConnectionHandler sender);

    /**
     * Called when connection is closed.
     * @param connection Closed connectionHandler
     */
    void onDisconnect(AbstractConnectionHandler connection);
}
