package SevenWonders;

import SevenWonders.Network.Client;
import SevenWonders.Network.Server;

import org.junit.jupiter.api.Test;


public class NetworkTest {

    @Test public void testClientShouldConnectServer() throws Exception {
        Server myServer = new Server();
        myServer.startServing();

        Client myClient = new Client("localhost", 8080, "someone");
        myClient.sendConnectRequest("my name");

        myClient.disconnect();
        Thread.sleep(1000);
    }

}
