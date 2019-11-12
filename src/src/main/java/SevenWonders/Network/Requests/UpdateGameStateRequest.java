package SevenWonders.Network.Requests;

public class UpdateGameStateRequest extends Request {

    // TODO: Implement GameModel
    private class GameModel {};

    public GameModel newGameModel;

    public static UpdateGameStateRequest of(GameModel model) {
        UpdateGameStateRequest updateGameStateRequest = new UpdateGameStateRequest();
        updateGameStateRequest.requestType = RequestType.UPDATE_GAME_STATE;
        updateGameStateRequest.newGameModel = model;
        return updateGameStateRequest;
    }
}
