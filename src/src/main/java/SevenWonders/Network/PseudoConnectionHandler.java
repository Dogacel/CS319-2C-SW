package SevenWonders.Network;

import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;

import java.net.Socket;

public class PseudoConnectionHandler implements IConnectionHandler {

    private User user;
    private INetworkListener listener;
    private AI_DIFFICULTY difficulty;

    PseudoConnectionHandler(INetworkListener listener, AI_DIFFICULTY difficulty, String username) {
        this.listener = listener;
        this.difficulty = difficulty;
        this.user = new User(username);
    }

    public AI_DIFFICULTY getDifficulty() {
        return difficulty;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void startListening() {

    }

    @Override
    public boolean receiveMessage() {
        return true;
    }

    @Override
    public void sendMessage(String message) {
        listener.receiveMessage(message, this);
    }

    @Override
    public void disconnect() {

    }
}
