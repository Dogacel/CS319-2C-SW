package SevenWonders.Network.Requests;

public enum RequestType {
    // Client to server
    CONNECT,
    KICK,
    START_GAME,
    ADD_AI_PLAYER,
    SELECT_WONDER,
    GET_READY,
    MAKE_MOVE,
    PLAYER_READY,

    // Server to client
    UPDATE_GAME_STATE,
    UPDATE_LOBBY,
    END_TURN,
    END_AGE,
    END_GAME,
}
