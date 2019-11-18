package SevenWonders.Network.Requests;

public class PlayerReadyRequest extends Request {
    boolean ready;
    public static PlayerReadyRequest of(boolean ready) {
        PlayerReadyRequest request = new PlayerReadyRequest();
        request.requestType = RequestType.PLAYER_READY;
        request.ready = ready;
        return request;
    }
}
