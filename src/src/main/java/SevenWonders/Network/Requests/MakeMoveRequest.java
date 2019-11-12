package SevenWonders.Network.Requests;

// TODO: Implement
public class MakeMoveRequest extends Request {

    // TODO: Implement MoveModel
    private class MoveModel {};

    public MoveModel move;  

    public static MakeMoveRequest of(MoveModel move) {
        MakeMoveRequest makeMoveRequest = new MakeMoveRequest();
        makeMoveRequest.requestType = RequestType.MAKE_MOVE;
        makeMoveRequest.move = move;
        return makeMoveRequest;
    }
}
