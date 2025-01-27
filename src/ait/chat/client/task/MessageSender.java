package ait.chat.client.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * The MessageSender class is responsible for sending messages to the server.
 * It runs in its own thread and sends user input messages along with the user's name and timestamp.
 */
public class MessageSender implements Runnable {
    private Socket socket;

    /**
     * Constructor that initializes the socket for communication with the server.
     *
     * @param socket The socket used to send messages to the server
     */
    public MessageSender(Socket socket) {
        this.socket = socket;
    }

    /**
     * The run method is executed when the thread starts.
     * It reads user input from the console, formats the message with the user's name and timestamp,
     * and sends it to the server until the user types "exit".
     */
    @Override
    public void run() {
        try (Socket socket = this.socket) {
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Enter your name");
            String name = br.readLine();

            System.out.println("Enter your message or type exit for quit");
            String message = br.readLine();

            while (!"exit".equalsIgnoreCase(message)) {
                socketWriter.println(name + " [" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + "] " + message);
                socketWriter.flush();
                message = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
