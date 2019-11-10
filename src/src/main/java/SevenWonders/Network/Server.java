package SevenWonders.Network;

import javafx.util.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {

	private class JSONParser{}
	private class JSONObject{}

	private ClientHandler[] clientHandlerList;
	private int clientCount;
	private ServerSocket serverSocket;
	private JSONParser jsonParser;
	private Object game;

	public static void main(String[] args) {
		GameServer server = new GameServer();
		while(server.acceptConnection());
	}

	public GameServer() {
		clientHandlerList = new ClientHandler[7];
		clientCount = 0;

		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (acceptConnection()) ;
	}

	public boolean acceptConnection() {
		try {
			Socket s = serverSocket.accept();
			System.out.println("New client connected : " + s);

			ClientHandler latestClient = new ClientHandler(s, clientCount, this);
			clientHandlerList[clientCount] = latestClient;
			clientCount++;
			new Thread(latestClient).start();

			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param receivedString
	 * @param clientID
	 */
	public void parseRequest(String receivedString, int clientID) {
		// TODO - implement GameServer.parseRequest
		throw new UnsupportedOperationException();
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