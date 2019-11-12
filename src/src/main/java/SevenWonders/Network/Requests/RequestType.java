package SevenWonders.Network.Requests;

public enum RequestType {
    SEND_TEXT, // For debugging
    JOIN,
    CONNECT,
    KICK,
    START_GAME,
    ADD_AI_PLAYER,
    SELECT_WONDER,
    UPDATE_GAME_STATE,
    MAKE_MOVE,
}
