package SevenWonders.Network;

import SevenWonders.Network.Requests.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements INetworkListener {

	private User user;
	private Gson gson;
	private ConnectionHandler connectionHandler;

	/**
	 * Establishes a connection to given address and port.
	 * @param serverAddress IP Address of the server
	 * @param serverPort Port of the server
	 */
	public Client(String serverAddress, int serverPort, String username) {
		try {
			InetAddress IP  = InetAddress.getByName(serverAddress);
			Socket socket = new Socket(IP, serverPort);
			gson = new Gson();

			this.user = new User(username);

			connectionHandler = new ConnectionHandler(socket, this);
			connectionHandler.startListening();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: Update move to MoveModel
	public void sendMakeMoveRequest(Object move) {
		MakeMoveRequest request = MakeMoveRequest.of(move);
	}

	// TODO: Update wonder
	public void sendSelectWonderRequest(String wonder) {
		SelectWonderRequest request = SelectWonderRequest.of(wonder);
		sendRequest(request);
	}

	// TODO: Update difficulty
	public void sendAddAIPlayerRequest(String difficulty) {
		if (!user.isAdmin) {
			// Unauthorized
			return;
		}

		AddAIPlayerRequest request = AddAIPlayerRequest.of(difficulty);
		sendRequest(request);
	}

	public void sendStartGameRequest() {
		if (!user.isAdmin) {
			// Unauthorized
			return;
		}

		StartGameRequest request = StartGameRequest.of();
		sendRequest(request);
	}

	public void sendKickRequest(String userToKick) {
		if (!user.isAdmin) {
			// Unauthorized
			return;
		}

		KickRequest request = KickRequest.of(userToKick);
		sendRequest(request);
	}

	public void sendConnectRequest(String username) {
		ConnectRequest request = ConnectRequest.of(username);
		sendRequest(request);
	}

	/**
	 * Send given request to the server
	 * @param r Request
	 */
	private void sendRequest(Request r) {
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