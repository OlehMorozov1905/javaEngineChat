package ait.chat.client.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The MessageReceiver class is responsible for receiving messages from the server
 * and printing them to the console.
 * It runs in its own thread and listens for incoming messages from the server.
 */
public class MessageReceiver implements Runnable {
    private Socket socket;

    /**
     * Constructor that initializes the socket for communication with the server.
     *
     * @param socket The socket used to receive messages from the server
     */
    public MessageReceiver(Socket socket) {
        this.socket = socket;
    }

    /**
     * The run method is executed when the thread starts.
     * It listens for incoming messages from the server and prints them to the console.
     */
    @Override
    public void run() {
        try {
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String message = socketReader.readLine();

                if (message == null) {
                    break;
                }

                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

