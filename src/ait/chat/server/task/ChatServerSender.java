package ait.chat.server.task;

import ait.mediation.BlkQueue;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The class responsible for sending messages to connected clients.
 * This class implements the Runnable interface to work in a separate thread.
 */
public class ChatServerSender implements Runnable {
    private BlkQueue<String> messageBox;
    private Set<PrintWriter> clients;

    /**
     * Constructor of the class.
     * @param messageBox The message queue that holds messages for broadcasting to clients
     */
    public ChatServerSender(BlkQueue<String> messageBox) {
        clients = new HashSet<>();
        this.messageBox = messageBox;
    }

    /**
     * Adds a new client to the list of connected clients.
     * @param socket The socket connection of the client
     * @return true if the client was added successfully
     * @throws IOException If an error occurs while getting the output stream from the socket
     */
    public synchronized boolean addClient(Socket socket) throws IOException {
        return clients.add(new PrintWriter(socket.getOutputStream(), true));
    }

    /**
     * The method that runs in a separate thread to send messages to clients.
     * Messages are read from the messageBox queue and sent to all connected clients.
     */
    @Override
    public void run() {
        while (true) {
            String message = messageBox.pop();

            synchronized (this) {
                Iterator<PrintWriter> iterator = clients.iterator();

                while (iterator.hasNext()) {
                    PrintWriter clientWriter = iterator.next();

                    if (clientWriter.checkError()) {
                        iterator.remove();
                    } else {
                        clientWriter.println(message);
                    }
                }
            }
        }
    }
}

