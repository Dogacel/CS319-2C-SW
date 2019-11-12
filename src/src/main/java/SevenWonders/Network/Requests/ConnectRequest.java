package SevenWonders.Network.Requests;

public class ConnectRequest extends Request {
    public String username;

    public static ConnectRequest of(String username) {
        ConnectRequest connectRequest = new ConnectRequest();
        connectRequest.requestType = RequestType.CONNECT;
        connectRequest.username = username;
        return connectRequest;
    }
}
