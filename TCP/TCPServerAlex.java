package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A TCP Server implementation for receiving messages from TCP Client.
 * <p>
 * The server waits for messages, writes them in the console, and sends back
 * the message to the client.
 * </p>
 * @see TCPClient
 * @see TCPMultipleServer For MultiTCP Connections
 */
public class TCPServer {

    private int port;
    private ServerSocket serverSocket;
    private static final int DEFAULT_PORT = 8080;

    /**
     * Constructs a TCPServer with the specified number port.
     * <p>
     * If the specified port requires sudoers permission, it chooses instead the default port 8080.
     * </p>
     * @param listeningPort the port of the server.
     */
    public TCPServer(String listeningPort) {
        this.port = Integer.parseInt(listeningPort);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = DEFAULT_PORT;
        }
    }

    /**
     * Constructs a TCPServer with a defined number port (8080).
     */
    public TCPServer() {
        this.port = DEFAULT_PORT;
        System.out.println("The TCP server started at the default port " + port);
    }

    /**
     * Launches the TCP server and manages incoming connections.
     *
     * @throws IOException if an error occurs while sending messages.
     */
    public void launch() throws IOException {
        try {
            startServer();
            listenForConnections();
        } catch (IOException e) {
            handleError(e);
        }
    }

    /**
     * Starts the server by initializing the server socket.
     *
     * @throws IOException if an error occurs while starting the server
     */
    private void startServer() throws IOException {
        serverSocket = new ServerSocket(this.port);
        System.out.println("TCP server launched at port " + this.port);
    }

    /**
     * Listens for incoming client connections and handles their communication.
     *
     * @throws IOException if an error occurs during communication
     */
    private void listenForConnections() throws IOException {
        Socket clientSocket = null;

        while (true) {
            if (clientSocket == null) {
                System.out.println("Waiting for a connection...");
                clientSocket = serverSocket.accept();
                System.out.println("Client connected from: " + clientSocket.getInetAddress());
            }

            handleClientCommunication(clientSocket);

            if (clientSocket.isClosed()) {
                clientSocket = null;
            }
        }
    }

    /**
     * Handles communication with a connected client.
     *
     * @param clientSocket the connected client socket
     * @throws IOException if an error occurs during communication
     */
    private void handleClientCommunication(Socket clientSocket) throws IOException {
        System.out.println("Waiting for a message...");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

        String clientMessage = in.readLine();
        System.out.println("Received message from client: " + clientMessage);

        out.println("Echo : " + clientMessage);

        if (shouldCloseConnection(clientMessage)) {
            clientSocket.close();
            System.out.println("End of connection.");
        }
    }

    /**
     * Determines if the server should close the connection with the client.
     *
     * @param message the client's message
     * @return true if the connection should be closed, false otherwise
     */
    private boolean shouldCloseConnection(String message) {
        return message == null || message.equalsIgnoreCase("exit");
    }

    /**
     * Handles any exceptions that occur during server operation.
     *
     * @param e the IOException to handle
     */
    private void handleError(IOException e) {
        System.out.println("Error: " + e.getMessage());
    }

    /**
     * Returns the current state of the socket for the TCP Server.
     *
     * @return String representation of the socket
     */
    @Override
    public String toString() {
        if (serverSocket == null) {
            return "Socket is not created yet.";
        } else if (serverSocket.isBound()) {
            return "Socket is bound to an address.";
        }
        return "Socket closed.";
    }

    /**
     * The main entry point for the TCPServer application.
     * <p>
     * Accepts optional argument to specify the server port. If no arguments are provided, it
     * uses default values according to the default constructor.
     * </p>
     * @param args optional argument: [port]
     * @throws IOException if an error occurs while running the server
     */
    public static void main(String[] args) throws IOException {
        TCPServer server;
        if (args.length > 0) {
            server = new TCPServer(args[0]);
        } else {
            server = new TCPServer();
        }
        server.launch();
    }
}
