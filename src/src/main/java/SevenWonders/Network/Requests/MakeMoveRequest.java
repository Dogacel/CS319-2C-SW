package SevenWonders.Network.Requests;

import SevenWonders.GameLogic.Move.MoveModel;

public class MakeMoveRequest extends Request {

    public MoveModel move;

    public static MakeMoveRequest of(MoveModel move) {
        MakeMoveRequest makeMoveRequest = new MakeMoveRequest();
        makeMoveRequest.requestType = RequestType.MAKE_MOVE;
        makeMoveRequest.move = move;
        return makeMoveRequest;
    }
}
