package SevenWonders.Network.Requests;

// TODO: Change aiDifficulty from String to enum
public class AddAIPlayerRequest extends AdminRequest {
    public String aiDifficulty;

    public static AddAIPlayerRequest of(String aiDifficulty) {
        AddAIPlayerRequest addAIPlayerRequest = new AddAIPlayerRequest();
        addAIPlayerRequest.requestType = RequestType.ADD_AI_PLAYER;
        addAIPlayerRequest.aiDifficulty = aiDifficulty;
        return addAIPlayerRequest;
    }
}
