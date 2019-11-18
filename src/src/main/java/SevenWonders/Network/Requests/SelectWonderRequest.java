package SevenWonders.Network.Requests;

import SevenWonders.GameLogic.Enums.WONDER_TYPE;

public class SelectWonderRequest extends Request {
    public WONDER_TYPE wonder;

    public static SelectWonderRequest of(WONDER_TYPE wonder) {
        SelectWonderRequest selectWonderRequest = new SelectWonderRequest();
        selectWonderRequest.requestType = RequestType.SELECT_WONDER;
        selectWonderRequest.wonder = wonder;
        return selectWonderRequest;
    }
}
