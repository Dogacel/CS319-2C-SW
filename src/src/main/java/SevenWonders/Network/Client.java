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

	/**
	 * Establishes a connection to given address and port.
	 * @param serverAddress IP Address of the server
	 * @param serverPort Port of the server
	 */
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

	/**
	 * Send given request to the server
	 * @param r Request
	 */
	public void sendRequest(Request r) {
		String message = gson.toJson(r, r.getClass());
		connectionHandler.sendMessage(message);
	}

	/**
	 * Called when client receives a message from the client.
	 * This method should parse the given request depending on its type.
	 * @param message Incoming String
	 * @param connectionHandler Connection to server
	 */
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