
# Chat Server and Client Application

## Project Description

This is a simple console chat application that implements both server and client parts for real-time message exchange between users over a network. The application utilizes sockets, multithreading, and blocking queues for efficient communication between the server and clients. It serves as a demonstration of basic and advanced skills in working with multithreading, network applications, and synchronization in Java.

### Key Features:
- **Multithreading:** The server handles multiple clients simultaneously using threads.
- **Blocking Queues:** A blocking queue is used to manage message communication between the server and clients.
- **Connection and Message Exchange:** Clients can connect to the server, send, and receive messages in real time.

## Technologies

- **Java 17**
- **Multithreading and threads**
- **Sockets for network communication**
- **Blocking queues for message synchronization**
- **BufferedReader and PrintWriter for input/output stream handling**

## Architecture

The application consists of two main components:

1. **Server Side (`ChatServerAppl`):**
   - Starts a server that listens for incoming client connections.
   - Handles connected clients using threads.
   - Maintains client connections in a collection of `PrintWriter` objects to send messages.

2. **Client Side (`ChatClientAppl`):**
   - Each client connects to the server and can send and receive messages.
   - Uses two threads: one for sending messages, another for receiving messages.

3. **Blocking Queue (`BlkQueue`):**
   - Serves as a buffer for messages between the server and clients.
   - Implemented using `ReentrantLock` and `Condition` for synchronization.

## How to Run

### 1. Running the Server

1. Open the project in **IntelliJ IDEA** or any other IDE that supports Java.
2. Go to the `ChatServerAppl` class and run it. The server will start listening on port 9000 for incoming connections.

   Example command:
   ```sh
   java ait.chat.server.ChatServerAppl
   ```

3. Once started, the console will show:
   ```
   server wait...
   ```
   This indicates that the server is waiting for connections.

### 2. Running the Client

1. Open a new terminal or a new IDE instance.
2. Go to the `ChatClientAppl` class and run it. The client will connect to the server on port 9000.

   Example command:
   ```sh
   java ait.chat.client.ChatClientAppl
   ```

3. After starting, you will be prompted to enter your name and then the text of the message.

   Example output:
   ```
   Enter your name
   John
   Enter your message or type exit for quit
   Hello, Server!
   ```

4. The server will broadcast the message to all connected clients.

### 3. Multiple Client Connections

1. To test, you can run multiple instances of the client application (e.g., in different terminals or IDE tabs).
2. Messages sent by one client will be visible to all other connected clients.

## Project Structure

```
src/
├── ait/
│   ├── chat/
│   │   ├── client/
│   │   │   ├── ChatClientAppl.java
│   │   │   └── task/
│   │   │       ├── MessageReceiver.java
│   │   │       └── MessageSender.java
│   │   ├── server/
│   │   │   ├── ChatServerAppl.java
│   │   │   └── task/
│   │   │       ├── ChatServerReceiver.java
│   │   │       └── ChatServerSender.java
│   ├── mediation/
│   │   ├── BlkQueue.java
│   │   └── BlkQueueImpl.java
```

### 1. `ChatServerAppl.java`:
Main class of the server side, which listens for connections and processes clients.

### 2. `ChatClientAppl.java`:
Main class of the client side, which connects to the server and sends/receives messages.

### 3. `BlkQueue.java` and `BlkQueueImpl.java`:
Implementation of a blocking queue to handle synchronization of message exchange between the server and clients.

### 4. `ChatServerReceiver.java` and `ChatServerSender.java`:
Classes that handle the receiving and sending of messages on the server side using multithreading.

### 5. `MessageReceiver.java` and `MessageSender.java`:
Classes that handle the receiving and sending of messages on the client side.

## Possible Improvements

1. **Error Handling:** Improve error handling to ensure the application doesn't crash on network errors or client disconnections.
2. **Authentication:** Implement an authentication system for clients.
3. **GUI:** Use JavaFX or Swing to create a more user-friendly interface.
4. **Logging:** Use logging libraries such as SLF4J and Logback for event and error tracking.
5. **Queue Optimization:** Develop a more flexible queue management system to avoid overflow issues.

## Conclusion

This is a simple and effective chat application that demonstrates core principles of multithreading, synchronization, and network communication in Java. It is ideal for beginner-level projects to demonstrate server-client application development and can be used in portfolios.
