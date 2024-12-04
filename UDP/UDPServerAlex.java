package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * A UDP Server implementation for receiving messages from UDP Client.
 * The server waits for messages then writes them in the console.
 * @see UDPClient
 */
public class UDPServer {

    private int port;
    private DatagramSocket socket;
    private static final int DEFAULT_PORT = 8080;
    private static final int MAX_PACKET_SIZE = 1500; // MTU default value for Ethernet packets

    /**
     * Constructs a UDPServer with the specified number port.
     *<p>
     * If the specified port requires sudoers permission, it chooses instead the default port 8080.
     *</p>
     * @param listeningPort the port of the server.
     */
    public UDPServer(String listeningPort) {
        this.port = Integer.parseInt(listeningPort);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will use the default port 8080 instead.");
            port = DEFAULT_PORT;
        }
    }

    /**
     * Constructs a UDPServer with a default port (8080).
     */
    public UDPServer() {
        this.port = DEFAULT_PORT; // Default port number
        System.out.println("The server will be opened with the default port number 8080. Normal usage: java Main [Listening Port].");
    }

    /**
     * Launches the server and handles incoming messages.
     *
     * @throws IOException if an error occurs during communication
     */
    public void launch() throws IOException {
        initializeSocket();
        receivePackets();
    }

    /**
     * Initializes the server socket and displays connection details.
     *
     * @throws IOException if an error occurs during socket initialization
     */
    private void initializeSocket() throws IOException {
        try {
            socket = new DatagramSocket(this.port);
            if (socket.getInetAddress() == null) {
                System.out.println("Socket opened at all addresses with Port: " + socket.getLocalPort());
            } else {
                System.out.println("Socket opened at address: " + socket.getInetAddress() + " Port: " + socket.getLocalPort());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize the socket.", e);
        }
    }

    /**
     * Receives packets from clients and processes them.
     *
     * @throws IOException if an error occurs while receiving packets
     */
    private void receivePackets() throws IOException {
        while (!socket.isClosed()) {
            byte[] dataReceived = new byte[MAX_PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(dataReceived, dataReceived.length);

            System.out.println("Waiting to receive packets.");
            socket.receive(packet);
            System.out.println("Packet received.");

            processPacket(packet);
        }
    }

    /**
     * Processes a received packet by extracting its data and source address.
     *
     * @param packet the received DatagramPacket
     */
    private void processPacket(DatagramPacket packet) {
        InetAddress sourceAddress = packet.getAddress();
        String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);

        System.out.println("[" + sourceAddress + "] " + message); // Format asked
    }

    /**
     * Returns the current state of the socket for the UDP Server.
     *
     * @return String representation of the socket
     */
    @Override
    public String toString() {
        if (socket == null) {
            return "Socket is not created yet.";
        } else if (socket.isBound()) {
            return "Socket is bound to an address.";
        } else if (socket.isConnected()) {
            return "Socket is connected.";
        }
        return "Socket closed.";
    }

    /**
     * The main entry point for the UDPServer application.
     * <p>
     * Accepts optional argument to specify the server port. If no arguments are provided, it
     * uses default values according to the default constructor.
     * </p>
     * @param args optional argument: [port]
     * @throws IOException if an error occurs while running the server
     */
    public static void main(String[] args) throws IOException {
        UDPServer server;
        if (args.length > 0) {
            server = new UDPServer(args[0]);
        } else {
            server = new UDPServer();
        }
        server.launch();
    }
}
