package SevenWonders.Network;

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

    public String getConnectionID() {return connectionID;}
    public void setConnectionID(String val){connectionID = val;}

    public ConnectionHandler(Socket s, INetworkListener listener) {
        this.socket = s;
        this.listener = listener;
        this.connectionID = s.toString();
        this.worker = new Thread(this);
        try {
            this.inputStream = new DataInputStream(s.getInputStream());
            this.outputStream = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startListening() {
        this.worker.start();
    }

    public void sendMessage(String message) {
        try {
            outputStream.writeUTF(message);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

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