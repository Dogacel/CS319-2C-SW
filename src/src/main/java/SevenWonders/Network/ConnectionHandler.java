package SevenWonders.Network;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ConnectionHandler implements Runnable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private INetworkListener listener;
    private String connectionID;
    private Thread worker;

    // Current user
    public User user;

    public boolean isAdmin() { return this.user.isAdmin; }
    public void setAdmin() { this.user.isAdmin = true; }

    public String getConnectionID() { return connectionID; }
    public void setConnectionID(String val){ connectionID = val; }

    /**
     * Establishes a connection to the given socket and listens to it.
     * @param s Socket
     * @param listener Listener that will parse requests
     */
    public ConnectionHandler(Socket s, INetworkListener listener) {
        this.socket = s;
        this.listener = listener;
        this.connectionID = s.toString();
        this.worker = new Thread(this);
        this.user = new User(connectionID);
        try {
            s.setTcpNoDelay(true);
            this.inputStream = new DataInputStream(s.getInputStream());
            this.outputStream = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start listening to the socket for incoming messages
     */
    public void startListening() {
        this.worker.start();
    }

    /**
     * Send the string to the connection.
     * @param message String message
     */
    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Receive a message from the socket
     * @return True if connection still exists.
     */
    public boolean receiveMessage() {
        try {
            String message = inputStream.readUTF();
            listener.receiveMessage(message, this);
            return true;
        } catch (IOException e) {
            if (e instanceof SocketException) {
                // Our connection closed
            } else if (e instanceof EOFException) {
                // Their connection closed
                listener.onDisconnect(this);
            } else {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Disconnect the socket
     */
    public void disconnect() {
        try {
            listener.onDisconnect(this);
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (receiveMessage());
    }
}