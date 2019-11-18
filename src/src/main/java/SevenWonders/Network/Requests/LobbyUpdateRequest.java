package SevenWonders.Network.Requests;

import SevenWonders.Network.AbstractConnectionHandler;
import SevenWonders.Network.User;

import java.util.Vector;

public class LobbyUpdateRequest extends Request {

    public User[] users;

    public static LobbyUpdateRequest of(Vector<AbstractConnectionHandler> handlers) {
        LobbyUpdateRequest lobbyUpdateRequest = new LobbyUpdateRequest();
        lobbyUpdateRequest.requestType = RequestType.UPDATE_LOBBY;
        lobbyUpdateRequest.users = new User[handlers.size()];
        int count = 0;
        for (AbstractConnectionHandler handler : handlers) {
            lobbyUpdateRequest.users[count] = handler.getUser();
            count++;
        }

        return lobbyUpdateRequest;
    }
}
