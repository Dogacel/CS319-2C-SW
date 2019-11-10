package SevenWonders;

public class Client {

	private Socket socket;
	private string serverAddress;
	private int serverPort;
	private JSONParser jsonParser;
	private ServerListener serverListener;

	public void receiveResponse() {
		// TODO - implement Client.receiveResponse
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param response
	 */
	public void parseResponse(string response) {
		// TODO - implement Client.parseResponse
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param isReady
	 */
	public JSONObject sendGetReadyRequest(boolean isReady) {
		// TODO - implement Client.sendGetReadyRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param move
	 */
	public JSONObject sendMakeMoveRequest(MoveModel move) {
		// TODO - implement Client.sendMakeMoveRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param trade
	 */
	public JSONObject sendUpdateMoveValidityRequest(TradeAction trade) {
		// TODO - implement Client.sendUpdateMoveValidityRequest
		throw new UnsupportedOperationException();
	}

	public JSONObject sendGetBoardInfoRequest() {
		// TODO - implement Client.sendGetBoardInfoRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param wonderName
	 */
	public JSONObject sendWonderSelectRequest(String wonderName) {
		// TODO - implement Client.sendWonderSelectRequest
		throw new UnsupportedOperationException();
	}

	public JSONObject sendConnectionRequest() {
		// TODO - implement Client.sendConnectionRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param newGameState
	 */
	public void onGameStateChanged(JSONObject newGameState) {
		// TODO - implement Client.onGameStateChanged
		throw new UnsupportedOperationException();
	}

	private void changeSceneToGameScene() {
		// TODO - implement Client.changeSceneToGameScene
		throw new UnsupportedOperationException();
	}

	private void changeSceneToScoreboardScene() {
		// TODO - implement Client.changeSceneToScoreboardScene
		throw new UnsupportedOperationException();
	}

}