package SevenWonders.Network.Requests;

import SevenWonders.GameLogic.GameModel;

public class UpdateGameStateRequest extends Request {

    public GameModel newGameModel;

    public static UpdateGameStateRequest of(GameModel model) {
        UpdateGameStateRequest updateGameStateRequest = new UpdateGameStateRequest();
        updateGameStateRequest.requestType = RequestType.UPDATE_GAME_STATE;
        updateGameStateRequest.newGameModel = model;
        return updateGameStateRequest;
    }
}
