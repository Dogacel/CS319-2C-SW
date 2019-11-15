package SevenWonders.Network;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class ConnectionHandler extends AbstractConnectionHandler implements Runnable {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    private Thread worker;

    private String connectionID;

    @Override
    public String toString() {
        return connectionID;
    }

    /**
     * Establishes a connection to the given socket and listens to it.
     * @param s Socket
     * @param listener Listener that will parse requests
     */
    ConnectionHandler(Socket s, INetworkListener listener) {
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
    @Override
    void startListening() {
        this.worker.start();
    }

    /**
     * Send the string to the connection.
     * @param message String message
     */
    @Override
    void sendMessage(String message) {
        LOGGER.info("Sending: " + message);
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
    @Override
    boolean receiveMessage() {
        try {
            String message = inputStream.readUTF();
            LOGGER.info("Received: " + message);
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
    @Override
    void disconnect() {
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