package SevenWonders.Network.ConsoleApplication;

import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.Network.Client;
import com.dosse.upnp.UPnP;

import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {

        UPnP.openPortTCP(8080);

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter server address:");

        String addr = sc.nextLine();

        Client c = new Client(addr, 8080, "DefaultUser");

        if (addr.equals("localhost")) {
            c.makeAdmin();
        }

        System.out.println("Please enter your name:");
        String in = sc.nextLine();
        c.sendConnectRequest(in);

        in = sc.nextLine();
        while (!in.equals("exit") && c.isConnected()) {
            if (in.startsWith("wonder")) {
                c.sendSelectWonderRequest(WONDER_TYPE.valueOf(in.replaceFirst("wonder ", "")));
            } else if (in.startsWith("start game")) {
                c.sendStartGameRequest();
            } else if (in.startsWith("add ai")) {
                try {
                    String str = in.replaceFirst("add ai ", "");
                    c.sendAddAIPlayerRequest(AI_DIFFICULTY.valueOf(str));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        if (c.isConnected()) {
            c.disconnect();
        }
    }
}
