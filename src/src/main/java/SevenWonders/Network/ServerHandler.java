package SevenWonders.Network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements Runnable {

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Client client;

    private class Sender implements Runnable {
        String message;
        public Sender(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                outputStream.writeUTF(message);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    public ServerHandler(Socket s, Client client) {
        this.socket = s;
        this.client = client;
        try {
            this.inputStream = new DataInputStream(s.getInputStream());
            this.outputStream = new DataOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendString(String string) {
        (new Sender(string)).run();
    }

    public boolean receiveRequest() {
        try {
            String message = inputStream.readUTF();
            System.out.println(message);
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