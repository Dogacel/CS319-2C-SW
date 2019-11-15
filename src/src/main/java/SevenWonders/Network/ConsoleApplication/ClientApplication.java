package SevenWonders.Network.ConsoleApplication;

import SevenWonders.Network.Client;

import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {

        Client c = new Client("localhost", 8080, "DefaultUser");
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your name:");
        String in = sc.nextLine();
        c.sendConnectRequest(in);

        in = sc.nextLine();
        while (!in.equals("exit")) {
            if (in.startsWith("wonder")) {
                c.sendSelectWonderRequest(in.replaceFirst("wonder ", ""));
            } else if (in.startsWith("start game")) {
                c.sendStartGameRequest();
            } else if (in.startsWith("add ai")) {
                c.sendAddAIPlayerRequest(in.replaceFirst("add ai ", ""));
            } else if (in.startsWith("kick")) {
                c.sendKickRequest(in.replaceFirst("kick ", ""));
            } else if (in.endsWith("ready")) {
                c.sendGetReadyRequest(!in.contains("unready"));
            } else if (in.startsWith("move")) {
                c.sendMakeMoveRequest(null);
            } else {
                System.out.println("Undefined request!");
            }
            in = sc.nextLine();
        }
        c.disconnect();
    }
}
