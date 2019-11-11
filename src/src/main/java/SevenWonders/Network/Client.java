package SevenWonders.Network;

import org.json.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements INetworkListener {

	private JSONObject jsonParser;
	private ConnectionHandler connectionHandler;

	public static void main(String[] args) {
		Client c = new Client("localhost", 8080);
		c.connectionHandler.startListening();

		Scanner sc = new Scanner(System.in);
		String in = sc.nextLine();
		while (!in.equals("exit")) {
			JSONObject myobj = new JSONObject();
			myobj.put("type", "text");
			myobj.put("text", in);

			c.sendMessage(myobj.toString());
			in = sc.nextLine();
		}
		c.connectionHandler.disconnect();
	}

	public Client(String serverAddress, int serverPort) {
		try {
			InetAddress IP  = InetAddress.getByName(serverAddress);
			Socket socket = new Socket(IP, serverPort);

			connectionHandler = new ConnectionHandler(socket, this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		connectionHandler.sendMessage(message);
	}

	@Override
    public void receiveMessage(String message, ConnectionHandler connectionHandler) {
	    JSONObject responseObject = new JSONObject(message);
	    String responseType = responseObject.getString("type");

	    if (responseType.equals("text")) {
	        System.out.println("Server: " + responseObject.getString("text"));
        }
    }

	@Override
	public void onDisconnect(ConnectionHandler connection) {

	}
}