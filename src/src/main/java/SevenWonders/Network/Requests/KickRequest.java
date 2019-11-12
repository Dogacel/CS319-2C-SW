package SevenWonders.Network.Requests;

public class KickRequest extends AdminRequest {
    public String username;

    public static KickRequest of(String username) {
        KickRequest kickRequest = new KickRequest();
        kickRequest.requestType = RequestType.KICK;
        kickRequest.username = username;
        return kickRequest;
    }
}
