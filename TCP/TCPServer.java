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
 * The server waits for messages then writes them in the console and sends back
 * the message to the client.
 * </p>
 * @see TCPClient
 * @see TCPMultipleServer For MultiTCP Connections
 */
public class TCPServer {

    private int port;
    private ServerSocket serverSocket;
    private static final int defaultPort = 8080;
    //private static final int max_size = 1500; // MTU default value for Ethernet packet

    /**
     * Constructs a TCPServer with the specified number port.
     *<p>
     * If the specified port requires sudoers permission, it chooses instead the default port 8080.
     *</p>
     * @param listeningPort the port of the server.
     */
    public TCPServer(String listeningPort) {
        this.port = Integer.parseInt(listeningPort);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = defaultPort;
        }
    }

    /**
     * Constructs a TCPServer with a defined number port (8080).
     */
    public TCPServer() {
        this.port = defaultPort;
        System.out.println("The TCP server started at the default port " + port);
    }

    /**
     * The main method of TCPServer.
     * <p>
     * Opens a socket and waits for a connection. When a client start a connection,
     * then the server waits for a message. After receiving a message, writes it in the console
     * and sends back to the client.
     * The connection is finished if the client ends its process (with exit or CTRL+D input)
     * </p>
     *
     * @throws IOException if an error occurs while sending messages.
     */
    public void launch() throws IOException{
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("TCP server launched at port " + this.port);

            // Waiting loop for connections
            Socket clientSocket = null;

            while (true) {
                if (clientSocket == null) {
                    System.out.println("Waiting for a connection...");
                    clientSocket = serverSocket.accept();
                }
                System.out.println("Waiting for a message...");
                System.out.println("Client connected from: " + clientSocket.getInetAddress());

                // In and Out streams
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                // Reading of data's client
                String clientMessage = in.readLine();
                System.out.println("Received message from client: " + clientMessage);

                // Answer
                out.println("Echo : " + clientMessage);

                // Closing of the connection
                if (clientMessage != null) {
                    if (clientMessage.equalsIgnoreCase("exit") | clientMessage.equals("null")) {
                        clientSocket.close();
                        System.out.println("End of connection.");
                        clientSocket = null;
                    }
                }
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
        TCPServer server;
        if (args.length > 0) {
            server = new TCPServer(args[0]);
        } else {
            server = new TCPServer();
        }
        server.launch();
    }
}

