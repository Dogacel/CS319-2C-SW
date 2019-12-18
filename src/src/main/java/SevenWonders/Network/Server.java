package SevenWonders.Network;

import SevenWonders.AI.AIMoveGenerator;
import SevenWonders.GameLogic.Enums.AI_DIFFICULTY;
import SevenWonders.GameLogic.Enums.WONDER_TYPE;
import SevenWonders.GameLogic.Game.GameController;
import SevenWonders.GameLogic.Game.GameModel;
import SevenWonders.GameLogic.Move.MoveModel;
import SevenWonders.Network.Requests.*;
import com.dosse.upnp.UPnP;
import com.google.gson.Gson;

import java.net.SocketException;
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

	private static Server serverInstance;
	private static Thread serverThread;

	private static Server createServerInstance() {
		System.out.println(UPnP.getExternalIP());
		System.out.println(UPnP.getLocalIP());
		serverInstance = new Server();
		return serverInstance;
	}

	public static void startServerInstance() {
		createServerInstance();
		serverThread = new Thread(serverInstance);
		serverThread.start();
	}

	public static void stopServerInstance() {
		if (serverThread != null) {
			try {
				serverInstance.serverSocket.close();
			} catch (IOException e) {
				// Don't care
			}
		}
	}

	public static Server getInstance() {
		return serverInstance;
	}

	private Vector<AbstractConnectionHandler> connectionHandlerList;
	private ServerSocket serverSocket;
	private Thread worker;
	private Gson gson;
	private GameModel gameModel;
	private GameController gameController;

	public Server() {
		connectionHandlerList = new Vector<>();
		gson = new Gson();
		try {
			serverSocket = new ServerSocket(18232);
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
				Socket s;
				try {
					s = serverSocket.accept();
				} catch (SocketException e) {
					LOGGER.warning("Socket closed!");
					return false;
				}

				LOGGER.info("New client connected : " + s);

				AbstractConnectionHandler latestConnection = new ConnectionHandler(s, this);

				if (s.getLocalSocketAddress().toString().startsWith(s.getInetAddress().toString())) {
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
		if (gameModel != null) {
			PseudoConnectionHandler replacement = new PseudoConnectionHandler(this, AI_DIFFICULTY.MEDIUM, "");
			replacement.user = connectionHandler.user;
			replacement.user.setUsername(replacement.user.getUsername()+" Bot");
			connectionHandlerList.add(replacement);
		}

		connectionHandlerList.remove(connectionHandler);
		LOGGER.warning("Client disconnected : " + connectionHandler);

	}

	/**
	 * Disconnect a client, similar to kick action.
	 * @param connectionHandler ConnectionHandler to disconnect
	 */
	private void disconnectClient(AbstractConnectionHandler connectionHandler) {
		connectionHandler.disconnect();
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
			case PLAYER_READY:
				parsePlayerReadyRequest(message, sender);
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}

	private void sendLobbyUpdateRequests() {
		LobbyUpdateRequest request = LobbyUpdateRequest.of(connectionHandlerList);
		for (AbstractConnectionHandler handler : connectionHandlerList) {
			sendRequest(request, handler);
		}
	}

	private void parsePlayerReadyRequest(String message, AbstractConnectionHandler sender) {
		PlayerReadyRequest request = gson.fromJson(message, PlayerReadyRequest.class);
		// TODO: Complete
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (!connectionHandler.getUser().isReady()) {
				return;
			}
		}

		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (connectionHandler instanceof  ConnectionHandler) {
				connectionHandler.getUser().setReady(false);
			}
		}

		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (connectionHandler instanceof PseudoConnectionHandler) {
				MoveModel aiMove = AIMoveGenerator.generateMove(gameModel.getPlayerList()[connectionHandler.getUser().getId()], ((PseudoConnectionHandler) connectionHandler).getDifficulty());
				if (gameController.checkMoveIsValid(aiMove)) {
					gameController.updateCurrentMove(aiMove.getPlayerID(), aiMove);
				}
			}
		}

		gameController.playTurn();

		sendUpdateGameStateRequests();

		sendUpdateGameStateRequests();
	}

	private void sendUpdateGameStateRequests() {
		UpdateGameStateRequest request = UpdateGameStateRequest.of(gameModel);
		for (AbstractConnectionHandler handler : connectionHandlerList) {
			sendRequest(request, handler);
		}
	}

	private void sendRequest(Request request, AbstractConnectionHandler receiver) {
		String message = gson.toJson(request, request.getClass());
		receiver.sendMessage(message);
	}

	private void parseGetReadyRequest(String message, AbstractConnectionHandler sender) {
		GetReadyRequest request = gson.fromJson(message, GetReadyRequest.class);
		sender.getUser().setReady(request.isReady);

		sendLobbyUpdateRequests();;
	}

	private void parseConnectRequest(String message, AbstractConnectionHandler sender) {
		ConnectRequest request = gson.fromJson(message, ConnectRequest.class);
		sender.getUser().setUsername(request.username);
		sendLobbyUpdateRequests();
	}

	private void parseKickRequest(String message, AbstractConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		KickRequest request = gson.fromJson(message, KickRequest.class);
		AbstractConnectionHandler found = null;
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (connectionHandler.getUser().getUsername().equals(request.username)) {
				found = connectionHandler;
				break;
			}
		}

		if (found != null) {
			disconnectClient(found);
			sendLobbyUpdateRequests();
		}
	}

	private void parseStartGameRequest(String message, AbstractConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		gameModel = new GameModel();
		gameController = new GameController(gameModel);

		for (AbstractConnectionHandler connection : connectionHandlerList) {
			int id = gameController.addPlayer(connection.getUser().getUsername(), connection.getUser().getSelectedWonder());
			connection.getUser().setId(id);
		}

		sendUpdateGameStateRequests();

	}

	private void parseAddAIPlayerRequest(String message, AbstractConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		if (connectionHandlerList.size() >= 7) {
			// Lobby full
			return;
		}

		AddAIPlayerRequest request = gson.fromJson(message, AddAIPlayerRequest.class);
		PseudoConnectionHandler pseudoConnectionHandler = new PseudoConnectionHandler(this, request.difficulty, request.difficulty.toString() + " BOT");
		pseudoConnectionHandler.getUser().setReady(true);
		connectionHandlerList.add(pseudoConnectionHandler);

		sendLobbyUpdateRequests();
	}

	private void parseMakeMoveRequest(String message, AbstractConnectionHandler sender) {
		MakeMoveRequest request = gson.fromJson(message, MakeMoveRequest.class);
		MoveModel move = request.move;
		assert move.getPlayerID() == sender.getUser().getId();
		if (move != null && gameController.checkMoveIsValid(move)) {
			gameController.updateCurrentMove(sender.getUser().getId(), move);
		}
	}

	private void parseWonderSelectRequest(String message, AbstractConnectionHandler sender) {
		SelectWonderRequest request = gson.fromJson(message, SelectWonderRequest.class);
		sender.getUser().setSelectedWonder(request.wonder);
		sender.getUser().setReady(true);

		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			if (!connectionHandler.getUser().isReady()) {
				return;
			}
		}

		sendStartGameRequests();
	}

	private void sendStartGameRequests() {
		int index = 0;
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			StartGameRequest request = StartGameRequest.of(index++);
			sendRequest(request, connectionHandler);
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}

		distributeWonders();

		gameModel = new GameModel();
		gameController = new GameController(gameModel);

		for (AbstractConnectionHandler connection : connectionHandlerList) {
			int id = gameController.addPlayer(connection.getUser().getUsername(), connection.getUser().getSelectedWonder());
			connection.getUser().setId(id);
			if (connection instanceof ConnectionHandler) {
				connection.getUser().setReady(false);
			}
		}

		gameController.dealCards();

		sendUpdateGameStateRequests();
	}

	/*
	 * Distributes wonders to connected users.
	 */
	private void distributeWonders() {

		// Holds a list of clients want to choose that wonder
		Map<WONDER_TYPE, Vector<AbstractConnectionHandler>> wonderCounts = new HashMap<>();

		// Iterate over all clients and add them to list of clients that want a wonder
		for (AbstractConnectionHandler connectionHandler : connectionHandlerList) {
			WONDER_TYPE wonder = connectionHandler.getUser().getSelectedWonder();
			Vector<AbstractConnectionHandler> connections = wonderCounts.get(wonder);
			if (connections == null) {
				connections = new Vector<>();
				connections.add(connectionHandler);
				wonderCounts.put(wonder, connections);
			} else {
				connections.add(connectionHandler);
			}
		}

		// Store which client will have which wonder
		Map<AbstractConnectionHandler, WONDER_TYPE> wonderUserMap = new HashMap<>();
		Vector<WONDER_TYPE> emptyWonders = new Vector<>();
		Vector<AbstractConnectionHandler> unassignedUsers = new Vector<>();

		// Iterate over all wonders and assign clients to wonders if only one client wants that wonder
		for (WONDER_TYPE wonder : WONDER_TYPE.values()) {
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

		wonderUserMap.forEach((conn, wonder) -> {
			conn.getUser().setSelectedWonder(wonder);
			System.out.println(conn.getUser().getUsername() + " : " + conn.getUser().getSelectedWonder().toString());
		});
	}
}