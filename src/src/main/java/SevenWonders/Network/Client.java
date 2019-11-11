package SevenWonders.Network;

import SevenWonders.Network.Requests.Request;
import SevenWonders.Network.Requests.RequestType;
import SevenWonders.Network.Requests.SendTextRequest;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements INetworkListener {

	private Gson gson;
	private ConnectionHandler connectionHandler;


	public Client(String serverAddress, int serverPort) {
		try {
			InetAddress IP  = InetAddress.getByName(serverAddress);
			Socket socket = new Socket(IP, serverPort);
			gson = new Gson();

			connectionHandler = new ConnectionHandler(socket, this);
			connectionHandler.startListening();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(Request r) {
		String message = gson.toJson(r, r.getClass());
		connectionHandler.sendMessage(message);
	}

	public void sendMessage(String message) {
		connectionHandler.sendMessage(message);
	}

	@Override
    public void receiveMessage(String message, ConnectionHandler connectionHandler) {
		Request dummyRequest = gson.fromJson(message, Request.class);

	    if (dummyRequest.requestType == RequestType.SEND_TEXT) {
			SendTextRequest request = gson.fromJson(message, SendTextRequest.class);
			System.out.println("Server: " + request.text);
        }
    }

    public void disconnect() {
		connectionHandler.disconnect();
	}

	@Override
	public void onDisconnect(ConnectionHandler connection) {
		System.out.println("Disconnected!");
	}
}