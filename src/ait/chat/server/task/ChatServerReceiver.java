package ait.chat.server.task;

import ait.mediation.BlkQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The class responsible for receiving messages from the client.
 * This class implements the Runnable interface to work in a separate thread.
 */
public class ChatServerReceiver implements Runnable {
    private Socket socket;
    private BlkQueue<String> messageBox;

    /**
     * Constructor of the class.
     * @param socket The socket through which the server receives data from the client
     * @param messageBox The message queue for passing received data to other components
     */
    public ChatServerReceiver(Socket socket, BlkQueue<String> messageBox) {
        this.socket = socket;
        this.messageBox = messageBox;
    }

    /**
     * Method that is executed in a separate thread to receive messages from the client.
     * Messages are read through the socket input stream and added to the message queue.
     */
    @Override
    public void run() {
        try (Socket socket = this.socket) {
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String message = socketReader.readLine();
                if (message == null) {
                    break;
                }

                messageBox.push(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

