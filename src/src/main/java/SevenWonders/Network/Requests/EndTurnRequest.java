package SevenWonders.Network.Requests;

public class EndTurnRequest extends Request {
    public static EndTurnRequest of() {
        EndTurnRequest endTurnRequest = new EndTurnRequest();
        endTurnRequest.requestType = RequestType.END_TURN;
        return endTurnRequest;
    }
}
