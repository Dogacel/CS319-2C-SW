package SevenWonders.Network;

import SevenWonders.Network.Requests.Request;
import SevenWonders.Network.Requests.SendTextRequest;
import com.google.gson.Gson;
import javafx.util.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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