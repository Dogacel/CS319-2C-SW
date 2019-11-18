package SevenWonders.Network.Requests;

public class EndAgeRequest extends Request {
    public static EndAgeRequest of() {
        EndAgeRequest endAgeRequest = new EndAgeRequest();
        endAgeRequest.requestType = RequestType.END_AGE;
        return endAgeRequest;
    }
}
