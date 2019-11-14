package SevenWonders.Network.Requests;

import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;

public class AddAIPlayerRequest extends AdminRequest {
    public AI_DIFFICULTY difficulty;

    public static AddAIPlayerRequest of(AI_DIFFICULTY difficulty) {
        AddAIPlayerRequest addAIPlayerRequest = new AddAIPlayerRequest();
        addAIPlayerRequest.requestType = RequestType.ADD_AI_PLAYER;
        addAIPlayerRequest.difficulty = difficulty;
        return addAIPlayerRequest;
    }
}
