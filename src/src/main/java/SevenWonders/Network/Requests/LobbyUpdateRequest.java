package SevenWonders.Network.Requests;

import SevenWonders.Network.AbstractConnectionHandler;
import SevenWonders.Network.User;

import java.util.Vector;

public class LobbyUpdateRequest extends Request {

    User[] users;

    public static LobbyUpdateRequest of(Vector<AbstractConnectionHandler> handlers) {
        LobbyUpdateRequest lobbyUpdateRequest = new LobbyUpdateRequest();

        lobbyUpdateRequest.users = new User[handlers.size()];
        int count = 0;
        for (AbstractConnectionHandler handler : handlers) {
            lobbyUpdateRequest.users[count] = handler.getUser();
            count++;
        }

        return lobbyUpdateRequest;
    }
}
