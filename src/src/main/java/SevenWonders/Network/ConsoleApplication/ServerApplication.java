package SevenWonders.Network.ConsoleApplication;

import SevenWonders.Network.Server;

public class ServerApplication {

    public static void main(String[] args) {
        Server server = new Server();
        server.startServing();
    }
}
