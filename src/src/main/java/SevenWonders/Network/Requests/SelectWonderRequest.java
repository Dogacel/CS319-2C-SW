package SevenWonders.Network.Requests;

// TODO: Implement wonder as enum
public class SelectWonderRequest extends Request {
    public String wonder;

    public static SelectWonderRequest of(String wonder) {
        SelectWonderRequest selectWonderRequest = new SelectWonderRequest();
        selectWonderRequest.requestType = RequestType.SELECT_WONDER;
        selectWonderRequest.wonder = wonder;
        return selectWonderRequest;
    }
}
