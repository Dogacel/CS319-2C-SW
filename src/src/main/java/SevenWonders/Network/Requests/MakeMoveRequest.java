package SevenWonders.Network.Requests;

// TODO: Implement MoveModel
public class MakeMoveRequest extends Request {

    public Object move;

    public static MakeMoveRequest of(Object move) {
        MakeMoveRequest makeMoveRequest = new MakeMoveRequest();
        makeMoveRequest.requestType = RequestType.MAKE_MOVE;
        makeMoveRequest.move = move;
        return makeMoveRequest;
    }
}
