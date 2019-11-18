package SevenWonders.Network.Requests;

public class StartGameRequest extends AdminRequest {
    public static StartGameRequest of() {
        StartGameRequest startGameRequest = new StartGameRequest();
        startGameRequest.requestType = RequestType.START_GAME;
        return startGameRequest;
    }
}
