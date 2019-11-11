package SevenWonders.Network;

import SevenWonders.Network.Requests.Request;
import SevenWonders.Network.Requests.SendTextRequest;
import com.google.gson.Gson;
import javafx.util.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;


public class Server implements Runnable, INetworkListener {

	private Vector<ConnectionHandler> connectionHandlerList;
	private ServerSocket serverSocket;
	private Thread worker;
	private Gson gson;
	private Object game;

	public Server() {
		connectionHandlerList = new Vector<>();
		gson = new Gson();
		try {
			serverSocket = new ServerSocket(8080);
			worker = new Thread(this);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void startServing() {
		worker.start();
	}

	@Override
	public void run() {
		while (acceptConnection()) ;
	}

	public boolean acceptConnection() {
		try {
			Socket s = serverSocket.accept();
			System.out.println("New client connected : " + s);

			ConnectionHandler latestConnection = new ConnectionHandler(s, this);

			// TODO: Change how to set someone admin
			if (connectionHandlerList.isEmpty()) {
				latestConnection.setAdmin();
			}

			connectionHandlerList.add(latestConnection);

			latestConnection.startListening();

			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public void receiveMessage(String message, ConnectionHandler sender) {

		Request requestInfo = gson.fromJson(message, Request.class);

		switch (requestInfo.requestType) {
			case SEND_TEXT:
				SendTextRequest request = gson.fromJson(message, SendTextRequest.class);
				System.out.println("Got: " + request.text + " from " + sender);
				sender.sendMessage(message);
				break;
			case JOIN:
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}

	@Override
	public void onDisconnect(ConnectionHandler connectionHandler) {
		connectionHandlerList.remove(connectionHandler);
		System.out.println("Client disconnected : " + connectionHandler.getConnectionID());
	}

	public void disconnectClient(ConnectionHandler connectionHandler) {
		connectionHandler.disconnect();
		onDisconnect(connectionHandler);
	}

	// TODO: Change to real wonders and update
	public void distributeWonders() {
		// Mocked wonders
		String[] mockWonders = {"A", "B", "C", "D", "E", "F", "G"};

		// Holds a list of clients want to choose that wonder
		Map<String, Vector<ConnectionHandler>> wonderCounts = new HashMap<>();

		// Iterate over all clients and add them to list of clients that want a wonder
		for (ConnectionHandler connectionHandler : connectionHandlerList) {
			String wonder = connectionHandler.user.selectedWonder;
			Vector<ConnectionHandler> connections = wonderCounts.get(wonder);
			if (connections == null) {
				connections = new Vector<ConnectionHandler>();
				connections.add(connectionHandler);
				wonderCounts.put(wonder, connections);
			} else {
				connections.add(connectionHandler);
			}
		}

		// Store which client will have which wonder
		Map<ConnectionHandler, String> wonderUserMap = new HashMap<>();
		Vector<String> emptyWonders = new Vector<>();
		Vector<ConnectionHandler> unassignedUsers = new Vector<>();

		// Iterate over all wonders and assign clients to wonders if only one client wants that wonder
		for (String wonder : mockWonders) {
			Vector<ConnectionHandler> connections = wonderCounts.get(wonder);
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

	public void parseJoinLobbyRequest(Gson requestBody) {

	}

	public Object generateMoveFromGson(Gson object) {
		// TODO - implement GameServer.generateMoveFromGson
		throw new UnsupportedOperationException();
	}

	public void updateGameStateForClients() {
		// TODO - implement GameServer.updateGameStateForClients
		throw new UnsupportedOperationException();
	}

	public void startNewGame() {
		// TODO - implement GameServer.startNewGame
		throw new UnsupportedOperationException();
	}

	private void generateAndPlayMovesForAIPlayers() {
		// TODO - implement GameServer.generateAndPlayMovesForAIPlayers
		throw new UnsupportedOperationException();
	}

	private Pair<Integer, Integer>[] distributeWondersForPlayers() {
		// TODO - implement GameServer.distributeWondersForPlayers
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseMoveValidityRequest(Gson object, int clientID) {
		// TODO - implement GameServer.parseMoveValidityRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseMakeMoveRequest(Gson object, int clientID) {
		// TODO - implement GameServer.parseMakeMoveRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseBoardInfoRequest(Gson object, int clientID) {
		// TODO - implement GameServer.parseBoardInfoRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseGetReadyRequest(Gson object, int clientID) {
		// TODO - implement GameServer.parseGetReadyRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseWonderSelectRequest(Gson object, int clientID) {
		// TODO - implement GameServer.parseWonderSelectRequest
		throw new UnsupportedOperationException();
	}


}