package SevenWonders.Network.Requests;

public class SendTextRequest extends  Request {

    public String text;

    public static SendTextRequest of(String text) {
       SendTextRequest str = new SendTextRequest();
       str.text = text;
       str.requestType = RequestType.SEND_TEXT;
       return str;
    }

}
