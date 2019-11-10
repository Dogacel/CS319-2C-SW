package SevenWonders;

public class ClientHandler implements Runnable {

	private Socket socket;
	private int clientID;
	private String username;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private GameServer server;

	/**
	 * 
	 * @param response
	 */
	public boolean sendResponse(string response) {
		// TODO - implement ClientHandler.sendResponse
		throw new UnsupportedOperationException();
	}

	public void receiveRequest() {
		// TODO - implement ClientHandler.receiveRequest
		throw new UnsupportedOperationException();
	}

}