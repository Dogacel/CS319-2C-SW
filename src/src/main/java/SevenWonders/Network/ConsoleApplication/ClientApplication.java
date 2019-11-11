package SevenWonders.Network.ConsoleApplication;

import SevenWonders.Network.Client;
import org.json.JSONObject;

import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {
        Client c = new Client("localhost", 8080);

        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        while (!in.equals("exit")) {
            JSONObject myobj = new JSONObject();
            myobj.put("type", "text");
            myobj.put("text", in);

            c.sendMessage(myobj.toString());
            in = sc.nextLine();
        }
        c.disconnect();
    }
}
