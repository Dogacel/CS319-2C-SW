package SevenWonders.Network.ConsoleApplication;

import SevenWonders.Network.Client;
import SevenWonders.Network.Requests.Request;
import SevenWonders.Network.Requests.RequestType;
import SevenWonders.Network.Requests.SendTextRequest;
import com.google.gson.Gson;

import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {

        Gson gson = new Gson();
        Client c = new Client("localhost", 8080);


        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        while (!in.equals("exit")) {
            c.sendRequest(SendTextRequest.of(in));
            in = sc.nextLine();
        }
        c.disconnect();
    }
}
