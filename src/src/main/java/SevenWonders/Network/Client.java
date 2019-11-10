package SevenWonders.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private class JSONParser {}
	private class JSONObject {}

	private JSONParser jsonParser;
	private ServerHandler sh;

	public static void main(String[] args) {
		Client c = new Client("localhost", 8080);
		Scanner sc = new Scanner(System.in);
		String in = sc.nextLine();
		while (!in.equals("exit")) {
			c.sendMessage(in);
			in = sc.nextLine();
		}
	}

	public Client(String serverAddress, int serverPort) {
		try {
			InetAddress IP  = InetAddress.getByName(serverAddress);
			Socket socket = new Socket(IP, serverPort);

			sh = new ServerHandler(socket, this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		this.sh.sendString(message);
	}

}