package SevenWonders.Network;

import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;

public class PseudoConnectionHandler extends AbstractConnectionHandler {

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
    void startListening() {

    }

    @Override
    boolean receiveMessage() {
        return true;
    }

    @Override
    void sendMessage(String message) {
        listener.receiveMessage(message, this);
    }

    @Override
    void disconnect() {

    }
}
