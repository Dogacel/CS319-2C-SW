package SevenWonders.Network;

import org.json.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements INetworkListener {

	private JSONObject jsonParser;
	private ConnectionHandler connectionHandler;


	public Client(String serverAddress, int serverPort) {
		try {
			InetAddress IP  = InetAddress.getByName(serverAddress);
			Socket socket = new Socket(IP, serverPort);

			connectionHandler = new ConnectionHandler(socket, this);
			connectionHandler.startListening();

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

    public void disconnect() {
		connectionHandler.disconnect();
	}

	@Override
	public void onDisconnect(ConnectionHandler connection) {

	}
}