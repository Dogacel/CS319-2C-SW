package SevenWonders.Network;

import javafx.util.Pair;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class Server implements Runnable, INetworkListener {

	private Vector<ConnectionHandler> connectionHandlerList;
	private ServerSocket serverSocket;
	private Thread worker;
	private Object game;


	public Server() {
		connectionHandlerList = new Vector<>();

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
		JSONObject receivedObject = new JSONObject(message);
		String requestType = receivedObject.getString("type");

		if (requestType.equals("text")) {
			System.out.println("Got: " + receivedObject.getString("text") + " from " + sender);
			sender.sendMessage(message);
		} else if (requestType.equals("connect")) {
			sender.setConnectionID(receivedObject.getString("id"));
		} else {
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

	/**
	 * 
	 * @param object
	 */
	public Object generateMoveFromJSONObject(JSONObject object) {
		// TODO - implement GameServer.generateMoveFromJSONObject
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
	private void parseMoveValidityRequest(JSONObject object, int clientID) {
		// TODO - implement GameServer.parseMoveValidityRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseMakeMoveRequest(JSONObject object, int clientID) {
		// TODO - implement GameServer.parseMakeMoveRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseBoardInfoRequest(JSONObject object, int clientID) {
		// TODO - implement GameServer.parseBoardInfoRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseGetReadyRequest(JSONObject object, int clientID) {
		// TODO - implement GameServer.parseGetReadyRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param object
	 * @param clientID
	 */
	private void parseWonderSelectRequest(JSONObject object, int clientID) {
		// TODO - implement GameServer.parseWonderSelectRequest
		throw new UnsupportedOperationException();
	}


}