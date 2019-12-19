package SevenWonders.Network.Requests;

public class StartGameRequest extends AdminRequest {
    public int id;
    public static StartGameRequest of(int id) {
        StartGameRequest startGameRequest = new StartGameRequest();
        startGameRequest.requestType = RequestType.START_GAME;
        startGameRequest.id = id;
        return startGameRequest;
    }
}
