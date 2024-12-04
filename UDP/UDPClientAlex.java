package UDP;

import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * A UDP Client implementation for sending messages to a UDP Server.
 * The client reads messages from the console, sends them to a UDP server,
 * and closes the connection when the user types "exit" or CTRL+D.
 * @see UDPServer
 */
public class UDPClient {

    private final int serverPort;
    private final String serverHost;

    /**
     * Constructs a UDPClient with the specified server hostname and port.
     *
     * @param host the hostname or IP address of the server
     * @param port the port number of the server
     */
    public UDPClient(String host, String port) {
        this.serverHost = host;
        this.serverPort = Integer.parseInt(port);
    }

    /**
     * Constructs a UDPClient with a defined hostname (localhost) and a defined number port (8080).
     */
    public UDPClient() {
        this.serverPort = 8080; // Default Port number
        this.serverHost = "localhost"; // Default Hostname
        System.out.println("The server will be opened with the default port number 8080 and localhost. Normal usage: java Main [Listening Port].");
    }

    /**
     * Launches the client and handles the communication with the server.
     *
     * @throws IOException if an error occurs during communication
     */
    public void launch() throws IOException {
        DatagramSocket socket = initializeSocket();
        Console console = initializeConsole();

        communicateWithServer(socket, console);
    }

    /**
     * Initializes the DatagramSocket and displays connection details.
     *
     * @return the initialized DatagramSocket
     * @throws IOException if an error occurs during socket initialization
     */
    private DatagramSocket initializeSocket() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        if (socket.getInetAddress() == null) {
            System.out.println("Socket opened at all addresses with Port: " + socket.getLocalPort());
        } else {
            System.out.println("Socket opened at address: " + socket.getInetAddress() + " Port: " + socket.getLocalPort());
        }
        return socket;
    }

    /**
     * Initializes the console for user input.
     *
     * @return the Console object
     */
    private Console initializeConsole() {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            System.exit(1);
        }
        System.out.println("Client started. Please type messages to send to the UDP Server. Type 'exit' to quit.");
        return console;
    }

    /**
     * Handles communication with the server.
     *
     * @param socket  the DatagramSocket used for communication
     * @param console the Console for user input
     * @throws IOException if an error occurs during communication
     */
    private void communicateWithServer(DatagramSocket socket, Console console) throws IOException {
        while (!socket.isClosed()) {
            String messageInput = console.readLine("Input: ");
            if ("exit".equalsIgnoreCase(messageInput) || messageInput == null) {
                socket.close();
                System.out.println("Client has exited.");
                break;
            }
            if (!messageInput.isEmpty()) {
                sendMessage(socket, messageInput);
            }
        }
    }

    /**
     * Sends a message to the UDP server.
     *
     * @param socket the DatagramSocket to use
     * @param message the message to send
     * @throws IOException if an error occurs while sending the message
     */
    private void sendMessage(DatagramSocket socket, String message) throws IOException {
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
        InetAddress address = InetAddress.getByName(serverHost);
        DatagramPacket packet = new DatagramPacket(data, data.length, address, serverPort);
        socket.send(packet);
        System.out.println("Message sent: " + message);
    }

    /**
     * The main entry point for the UDPClient application.
     * <p>
     * Accepts optional arguments to specify the server host and port. If no arguments are provided, it
     * uses default values according to the default constructor.
     * </p>
     * @param args optional arguments: [host] [port]
     * @throws IOException if an error occurs while running the client
     */
    public static void main(String[] args) throws IOException {
        UDPClient client;
        if (args.length > 0) {
            client = new UDPClient(args[0], args[1]);
        } else {
            client = new UDPClient();
        }
        client.launch();
    }
}
