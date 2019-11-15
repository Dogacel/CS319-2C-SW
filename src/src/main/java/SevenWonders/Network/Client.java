package SevenWonders.Network;

import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.GameLogic.MoveModel;
import SevenWonders.Network.Requests.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Logger;

public class Client implements INetworkListener {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private User user;
	private Gson gson;
	private ConnectionHandler connectionHandler;

	public void makeAdmin() {
		this.user.setAdmin(true);
	}

	public boolean isConnected() {
		return connectionHandler.isConnected();
	}

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

	private void onEndGameRequest() {
		// TODO: Change to score view
	}

	private void onEndAgeRequest() {
		// TODO: Battle screen?
	}

	private void onEndTurnRequest() {
		// TODO: Unimplemented
	}

	private void onStartGameRequest() {
		// TODO: Change view to game-play view
	}

	private void onUpdateGameStateRequest(String message) {
		UpdateGameStateRequest request = gson.fromJson(message, UpdateGameStateRequest.class);
		// TODO: Implement interaction between UI and Client
	}

	public void sendGetReadyRequest(boolean ready) {
		GetReadyRequest request = GetReadyRequest.of(ready);
		sendRequest(request);
	}

	public void sendMakeMoveRequest(MoveModel move) {
		MakeMoveRequest request = MakeMoveRequest.of(move);
		sendRequest(request);
	}

	// TODO: Update wonder
	public void sendSelectWonderRequest(String wonder) {
		SelectWonderRequest request = SelectWonderRequest.of(wonder);
		sendRequest(request);
	}

	// TODO: Update difficulty
	public void sendAddAIPlayerRequest(AI_DIFFICULTY difficulty) {
		if (!user.isAdmin()) {
			// Unauthorized
			return;
		}

		AddAIPlayerRequest request = AddAIPlayerRequest.of(difficulty);
		sendRequest(request);
	}

	public void sendStartGameRequest() {
			if (!user.isAdmin()) {
			// Unauthorized
			return;
		}

		StartGameRequest request = StartGameRequest.of();
		sendRequest(request);
	}

	public void sendKickRequest(String userToKick) {
		if (!user.isAdmin()) {
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
	public void receiveMessage(String message, AbstractConnectionHandler connectionHandler) {

		Request request = gson.fromJson(message, Request.class);

	    switch (request.requestType) {
			case START_GAME:
				onStartGameRequest();
				break;
			case UPDATE_GAME_STATE:
				onUpdateGameStateRequest(message);
				break;
			case END_TURN:
				onEndTurnRequest();
				break;
			case END_AGE:
				onEndAgeRequest();
				break;
			case END_GAME:
				onEndGameRequest();
				break;
			default:
				throw new UnsupportedOperationException();
        }
    }

    public void disconnect() {
		connectionHandler.disconnect();
	}

	@Override
	public void onDisconnect(AbstractConnectionHandler connection) {
		LOGGER.warning("Disconnected!");
	}
}