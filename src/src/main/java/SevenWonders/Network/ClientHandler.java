package SevenWonders.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket socket;
	private int clientID;
	private String username;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private Server server;

	public ClientHandler(Socket s, int cid, Server ser) {
		this.clientID = cid;
		this.socket = s;
		this.server = ser;
		try {
			this.inputStream = new DataInputStream(socket.getInputStream());
			this.outputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	/**
	 * 
	 * @param response
	 */
	public boolean sendResponse(String response) {
		// TODO - implement ClientHandler.sendResponse
		throw new UnsupportedOperationException();
	}

	public boolean receiveRequest() {
		try {
			String received = inputStream.readUTF();
			if (received.equals("exit")) {
				return false;
			}

			System.out.println("Got: " + received + " from " + clientID);

			return true;
		} catch (IOException exception) {
			exception.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
		while (receiveRequest());
	}
}