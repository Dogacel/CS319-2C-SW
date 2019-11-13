package SevenWonders.Network;

import SevenWonders.Network.Requests.*;
import com.google.gson.Gson;

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

	/**
	 * Start listening to the incoming connections.
	 */
	public void startServing() {
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
			Socket s = serverSocket.accept();
			System.out.println("New client connected : " + s);

			ConnectionHandler latestConnection = new ConnectionHandler(s, this);

			// TODO: Change how to set someone admin
			if (connectionHandlerList.isEmpty()) {
				latestConnection.getUser().setAdmin(true);
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
	public void onDisconnect(ConnectionHandler connectionHandler) {
		connectionHandlerList.remove(connectionHandler);
		System.out.println("Client disconnected : " + connectionHandler);
		// TODO: Add AI Player if needed
	}

	/**
	 * Disconnect a client, similar to kick action.
	 * @param connectionHandler ConnectionHandler to disconnect
	 */
	private void disconnectClient(ConnectionHandler connectionHandler) {
		connectionHandler.disconnect();
		onDisconnect(connectionHandler);
	}

	/**
	 * Receive a message from a client
	 * @param message String of a JSONObject, it extends Request type
	 * @param sender Sender of the request
	 */
	@Override
	public void receiveMessage(String message, ConnectionHandler sender) {

		Request requestInfo = gson.fromJson(message, Request.class);

		switch (requestInfo.requestType) {
			case SEND_TEXT:
				SendTextRequest request = gson.fromJson(message, SendTextRequest.class);
				System.out.println("Got: " + request.text + " from " + sender.getUser().getUsername());
				sender.sendMessage(message);
				break;
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
			case MAKE_MOVE:
				parseMakeMoveRequest(message, sender);
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}

	private void parseConnectRequest(String message, ConnectionHandler sender) {
		ConnectRequest request = gson.fromJson(message, ConnectRequest.class);
		sender.getUser().setUsername(request.username);
	}

	private void parseKickRequest(String message, ConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		KickRequest request = gson.fromJson(message, KickRequest.class);
		for (ConnectionHandler connectionHandler : connectionHandlerList) {
			if (connectionHandler.getUser().getUsername().equals(request.username)) {
				disconnectClient(connectionHandler);
				break;
			}
		}
	}

	private void parseStartGameRequest(String message, ConnectionHandler sender) {
		// TODO: Start game
		// TODO: Send UpdateGameStateRequest
	}

	private void parseAddAIPlayerRequest(String message, ConnectionHandler sender) {
		if (!sender.getUser().isAdmin()) {
			// Unauthorized
			return;
		}

		AddAIPlayerRequest request = gson.fromJson(message, AddAIPlayerRequest.class);
		// TODO: Initialize AI Player as pseudoConnectionHandler and add it to the connectionHandlerList
	}

	private void parseMakeMoveRequest(String message, ConnectionHandler sender) {
		MakeMoveRequest request = gson.fromJson(message, MakeMoveRequest.class);
		// TODO: Implement make move functionality
	}

	private void parseWonderSelectRequest(String message, ConnectionHandler sender) {
		SelectWonderRequest request = gson.fromJson(message, SelectWonderRequest.class);
		// TODO: Implement select wonder functionality
	}


	/*
	 * Distributes wonders to connected users.
	 * TODO: Add a test
	 */
	private void distributeWonders() {
		// TODO: Change to real wonders and update
		String[] mockWonders = {"A", "B", "C", "D", "E", "F", "G"};

		// Holds a list of clients want to choose that wonder
		Map<String, Vector<ConnectionHandler>> wonderCounts = new HashMap<>();

		// Iterate over all clients and add them to list of clients that want a wonder
		for (ConnectionHandler connectionHandler : connectionHandlerList) {
			String wonder = connectionHandler.getUser().getSelectedWonder();
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
}