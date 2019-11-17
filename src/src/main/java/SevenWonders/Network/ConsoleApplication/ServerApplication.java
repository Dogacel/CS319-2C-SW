package SevenWonders.Network.ConsoleApplication;

import SevenWonders.Network.Server;

import com.dosse.upnp.UPnP;

public class ServerApplication {

    public static void main(String[] args) {
        UPnP.openPortTCP(8080);
        Server server = new Server();
        server.startServing();
    }
}
