package SevenWonders;

import SevenWonders.Network.Client;
import SevenWonders.Network.Server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class NetworkTest {

    private static PrintStream sysOut;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeAll
    public static void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void revertStreams() {
        System.setOut(sysOut);
    }

    @Test public void testClientShouldConnectServer() throws Exception {
        Server myServer = new Server();
        myServer.startServing();

        Client myClient = new Client("localhost", 8080, "someone");
        myClient.sendConnectRequest("my name");
        myClient.sendTextRequest("selam");

        Thread.sleep(100);

        assert outContent.toString().contains("Got: selam from my name");

        myClient.disconnect();
        Thread.sleep(100);

        assert outContent.toString().contains("Disconnected!");
    }

}
