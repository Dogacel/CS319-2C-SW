package SevenWonders.Network.Requests;

public class GetReadyRequest extends Request {
    public boolean isReady;

    public static GetReadyRequest of(boolean ready) {
        GetReadyRequest request = new GetReadyRequest();
        request.requestType = RequestType.GET_READY;
        request.isReady = ready;
        return request;
    }
}
