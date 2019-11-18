package SevenWonders.Network;

import SevenWonders.Network.Requests.UpdateGameStateRequest;

public interface IGameListener {
    void onUpdateGameStateRequest(UpdateGameStateRequest request);
    void onEndGameRequest();
    void onEndAgeRequest();
    void onEndTurnRequest();
    void onDisconnect();
}
