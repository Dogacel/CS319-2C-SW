package SevenWonders.Network.Requests;

public enum RequestType {
    // Client to server
    SEND_TEXT, // For debugging
    CONNECT,
    KICK,
    START_GAME,
    ADD_AI_PLAYER,
    SELECT_WONDER,
    MAKE_MOVE,

    // Server to client
    UPDATE_GAME_STATE,
    END_TURN,
    END_AGE,
    END_GAME,
}
