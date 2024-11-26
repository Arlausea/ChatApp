package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    private int port;
    private DatagramSocket socket;
    private static final int defaultPort = 8080;
    private static final int maxSize = 1500; // MTU default value for Ethernet packet


    /**
     * Constructor with one parameter to choose our listening port for the UDP Server.
     * @param listeningPort
     */
    public UDPServer(String listeningPort) {
        this.port = Integer.parseInt(listeningPort);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = defaultPort;
        }
    }

    /**
     * Default constructor of the UDP Server with a default port equal to 8080.
     */
    public UDPServer() {
        this.port = defaultPort; // Default port number
        System.out.println("The server will be opened with the default port number 8080. Normal usage: java Main [Listening Port].");
    }

    public void launch() throws IOException {

        try {
            socket = new DatagramSocket(this.port);
            if (socket.getInetAddress() == null) {
                System.out.println("Socket opened at all addresses with Port: " + socket.getLocalPort());
            } else {
                System.out.println("Socket opened at address: " + socket.getInetAddress() + " Port: " + socket.getLocalPort());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Creation of a DatagramPacket

        while (!(socket.isClosed())) {
            byte[] dataReceived = new byte[maxSize];
            DatagramPacket packet = new DatagramPacket(dataReceived, dataReceived.length);
            System.out.println("Waiting to receive packets.");
            socket.receive(packet);
            System.out.println("Packet received");

            InetAddress sourceAddress = packet.getAddress();
            dataReceived = packet.getData();
            String dataReceivedToString = new String(dataReceived, StandardCharsets.UTF_8);
            System.out.println("[" + sourceAddress.toString() + "] " + dataReceivedToString); // Format asked
        }

    }

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