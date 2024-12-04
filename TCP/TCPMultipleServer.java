package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * A TCP Server implementation for receiving messages from multiple TCP Client.
 * <p>
 * The server waits for messages then writes them in the console and sends back
 * the message to the original client. the message is not send to other clients.
 * </p>
 * @see TCPServer
 */
public class TCPMultipleServer {

    private int port;
    private ServerSocket serverSocket;
    private static final int defaultPort = 8080;
   // private static final int max_size = 1500; // MTU default value for Ethernet packet

    /**
     * Constructs a TCPMultipleServer with the specified number port.
     *<p>
     * If the specified port requires sudoers permission, it chooses instead the default port 8080.
     *</p>
     * @param listeningPort the port of the server.
     */
    public TCPMultipleServer(String listeningPort) {
        this.port = Integer.parseInt(listeningPort);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = defaultPort;
        }
    }

    /**
     * Constructs a TCPMultipleServer with a defined number port (8080).
     */
    public TCPMultipleServer() {
        this.port = defaultPort;
        System.out.println("The TCP server started at the default port " + port);
    }

    /**
     * The main method of TCPMultipleServer.
     * <p>
     *
     * Opens a socket and waits for a connection. When a client start a connection,
     * then the server start a thread with the client socket to receive and send back messages.
     * The connection is finished if the client ends its process (with exit or CTRL+D input)
     * </p>
     *
     * @throws IOException if an error occurs while creating threads.
     */
    public void launch() throws IOException {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("TCP server launched at port " + this.port);

            // Waiting loop for connection
            Socket clientSocket = null;

            while (true) {
                    System.out.println("Waiting for a connection...");
                    clientSocket = serverSocket.accept();
                    ConnectionThread connectionThread = new ConnectionThread(clientSocket);
                    Thread thread = new Thread(connectionThread);
                    thread.start();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        TCPMultipleServer server;
        if (args.length > 0) {
            server = new TCPMultipleServer(args[0]);
        } else {
            server = new TCPMultipleServer();
        }
        server.launch();
    }
}

