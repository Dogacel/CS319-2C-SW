package SevenWonders.Network;

import SevenWonders.Network.Requests.LobbyUpdateRequest;
import SevenWonders.Network.Requests.StartGameRequest;

public interface ILobbyListener {
    void onUpdateLobbyRequest(LobbyUpdateRequest request);
    void onStartGameRequest();
    void onDisconnect();
}
