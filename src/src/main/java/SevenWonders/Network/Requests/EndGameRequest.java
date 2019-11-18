package SevenWonders.Network.Requests;

public class EndGameRequest extends Request {
    public static EndGameRequest of() {
        EndGameRequest endGameRequest = new EndGameRequest();
        endGameRequest.requestType = RequestType.END_GAME;
        return endGameRequest;
    }
}
