package SevenWonders.Network;

import SevenWonders.GameLogic.GameModel;
import SevenWonders.Network.Requests.*;
import com.google.gson.Gson;

import java.util.logging.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Server implements Runnable, INetworkListener {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private Vector<AbstractConnectionHandler> connectionHandlerList;
	private ServerSocket serverSocket;
	private Thread worker;
	private Gson gson;
	private GameModel gameModel;

	public Server() {
		connectionHandlerList = new Vector<>();
		gson = new Gson();
		try {
			serverSocket = new ServerSocket(8080);
			LOGGER.info(serverSocket.toString());
			worker = new Thread(this);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Start listening to the incoming connections.
	 */
	public void startServing() {
		LOGGER.info("Start serving.");
		worker.start();
	}

	@Override
	public void run() {
		while (acceptConnection()) ;
	}

	/**
	 * Accept connection when a new client tries to connect
	 * @return False if an exception occurs
	 */
	private boolean acceptConnection() {
		try {
			if (connectionHandlerList.size() < 7) {
				Socket s = serverSocket.accept();
				LOGGER.info("New client connected : " + s);

				AbstractConnectionHandler latestConnection = new ConnectionHandler(s, this);

				// TODO: Change how to set someone admin
				if (connectionHandlerList.isEmpty()) {
					latestConnection.getUser().setAdmin(true);
				}

				connectionHandlerList.add(latestConnection);

				latestConnection.startListening();

			}
			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public void onDisconnect(AbstractConnectionHandler connectionHandler) {
		connectionHandlerList.remove(connectionHandler);
		LOGGER.warning("Client disconnected : " + connectionHandler);
		// TODO: Add AI Player if needed
	}

	/**
	 * Disconnect a client, similar to kick action.
	 * @param connectionHandler ConnectionHandler to disconnect
	 */
	private void disconnectClient(AbstractConnectionHandler connectionHandler) {
		connectionHandler.disconnect();
		onDisconnect(connectionHandler);
	}

	/**
	 * Receive a message from a client
	 * @param message String of a JSONObject, it extends Request type
	 * @param sender Sender of the request
	 */
	@Override
	public void receiveMessage(String message, AbstractConnectionHandler sender) {

		Request requestInfo = gson.fromJson(message, Request.class);;

		switch (requestInfo.requestType) {
			case CONNECT:
				parseConnectRequest(message, sender);
				break;
			case KICK:
				parseKickRequest(message, sender);
				break;
			case START_GAME:
				parseStartGameRequest(message, sender);
				break;
			case ADD_AI_PLAYER:
				parseAddAIPlayerRequest(message, sender);
				break;
			case SELECT_WONDER:
				parseWonderSelectRequest(message, sender);
				break;
			case GET_READY:
				parseGetReadyRequest(message, sender);
				break;
			case MAKE_MOVE:
				parseMakeMoveRequest(message, sender);
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}

	private void sendUpdateGameStateRequest(AbstractConnectionHandler receiver) {
		UpdateGameStateRequest request = UpdateGameStateRequest.of(gameModel);
		sendRequest(request, receiver);
	}

	private void sendRequest(Request request, AbstractConnectionHandler receiver) {
		String message = gson.toJson(request, request.getClass());
		receiver.sendMessage(message);
	}

	private void parseGetReadyRequest(String message, AbstractConnectionHandler sender) {
		GetReadyRequest request = gson.fromJson(message, GetReadyRequest.class);
		sender.getUser().setReady(request.isReady);
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (!connectionHandler.getUser().isReady()) {
				return;
			}
		}
		// TODO: play the turn if everyone is ready
	}

	private void parseConnectRequest(String message, AbstractConnectionHandler sender) {
		ConnectRequest request = gson.fromJson(message, ConnectRequest.class);
		sender.getUser().setUsername(request.username);
	}

	private void parseKickRequest(String message, AbstractConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		KickRequest request = gson.fromJson(message, KickRequest.class);
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (connectionHandler.getUser().getUsername().equals(request.username)) {
				disconnectClient(connectionHandler);
				break;
			}
		}
	}

	private void parseStartGameRequest(String message, AbstractConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}
		// TODO: Start game
		gameModel = new GameModel();

		for (AbstractConnectionHandler connection : connectionHandlerList) {
			sendUpdateGameStateRequest(connection);
		}
	}

	private void parseAddAIPlayerRequest(String message, AbstractConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		AddAIPlayerRequest request = gson.fromJson(message, AddAIPlayerRequest.class);
		PseudoConnectionHandler pseudoConnectionHandler = new PseudoConnectionHandler(this, request.difficulty, "name");
		// TODO: Initialize AI Player as pseudoConnectionHandler and add it to the connectionHandlerList
	}

	private void parseMakeMoveRequest(String message, AbstractConnectionHandler sender) {
		MakeMoveRequest request = gson.fromJson(message, MakeMoveRequest.class);
		// TODO: Implement make move functionality
		// MoveController.isMoveValid ? => game.setMove(move, sender.getUser())
	}

	private void parseWonderSelectRequest(String message, AbstractConnectionHandler sender) {
		SelectWonderRequest request = gson.fromJson(message, SelectWonderRequest.class);
		sender.getUser().setSelectedWonder(request.wonder);
	}

	/*
	 * Distributes wonders to connected users.
	 * TODO: Add a test
	 */
	private void distributeWonders() {
		// TODO: Change to real wonders and update
		String[] mockWonders = {"A", "B", "C", "D", "E", "F", "G"};

		// Holds a list of clients want to choose that wonder
		Map<String, Vector<AbstractConnectionHandler>> wonderCounts = new HashMap<>();

		// Iterate over all clients and add them to list of clients that want a wonder
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			String wonder = connectionHandler.getUser().getSelectedWonder();
			Vector<AbstractConnectionHandler> connections = wonderCounts.get(wonder);
			if (connections == null) {
				connections = new Vector<AbstractConnectionHandler>();
				connections.add(connectionHandler);
				wonderCounts.put(wonder, connections);
			} else {
				connections.add(connectionHandler);
			}
		}

		// Store which client will have which wonder
		Map<AbstractConnectionHandler, String> wonderUserMap = new HashMap<>();
		Vector<String> emptyWonders = new Vector<>();
		Vector<AbstractConnectionHandler> unassignedUsers = new Vector<>();

		// Iterate over all wonders and assign clients to wonders if only one client wants that wonder
		for (String wonder : mockWonders) {
			Vector<AbstractConnectionHandler> connections = wonderCounts.get(wonder);
			// Store unassigned wonders in emptyWonders
			if (connections == null) {
				emptyWonders.add(wonder);
			} else if (connections.size() == 1) {
				wonderUserMap.put(connections.firstElement(), wonder);
			} else {
				// If more than 1 client wants a wonder, choose a random client
				Random r = new Random();
				int randomIndex = r.nextInt(connections.size());
				for (int i = 0 ; i < connections.size() ; i++) {
					if (i == randomIndex) {
						wonderUserMap.put(connections.get(i), wonder);
					} else {
						// Store other users in unassigned users
						unassignedUsers.add(connections.get(i));
					}
				}
			}
		}

		// Number of unassigned users should match number of unassigned wonders
		assert unassignedUsers.size() == emptyWonders.size();

		// Distribute unassigned users to unassigned wonders
		for (int i = 0 ; i < unassignedUsers.size() ; i++) {
			wonderUserMap.put(unassignedUsers.get(i), emptyWonders.get(i));
		}
	}
}