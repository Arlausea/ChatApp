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
 * and closes the connection when the user types "exit".
 * @see UDPServer
 */
public class UDPClient {

    private final int ServerPort;
    private final String ServerHost;


    /**
     * Constructs a UDPClient with the specified server hostname and port.
     *
     * @param host the hostname or IP address of the server
     * @param port the port number of the server
     */
    public UDPClient(String host, String port) {
        this.ServerHost = host;
        this.ServerPort = Integer.parseInt(port);
    }

    /**
     * Constructs a UDPClient with a defined hostname (localhost) and a defined number port (8080).
     */
    public UDPClient() {
        this.ServerPort = 8080; // Default Port number
        this.ServerHost = "localhost"; // Default Hostname
        System.out.println("The server will be opened with the default port number 8080 and localhost. Normal usage: java Main [Listening Port].");
    }


    /**
     * The main method of UDPClient.
     * <p>
     * Opens a socket and sends messages
     * to the UDP Server. If no input is provided, the client sends nothing.
     * If the input is "exit", stops the connection and closes the socket.
     * </p>
     *
     * @throws IOException if an error occurs while sending messages.
     */
    public void launch() throws IOException {
        try {
            DatagramSocket socket = new DatagramSocket();
            if (socket.getInetAddress() == null){
                System.out.println("Socket opened at all addresses with Port: " + socket.getLocalPort());
            }
            else{
                System.out.println("Socket opened at address: "+ socket.getInetAddress() + " Port: " + socket.getLocalPort());
            }
            Console console = System.console();

            if (console == null) {
                System.out.println("No console available");
                return;
            }
            System.out.println("Client started. Please type messages to send to the UDP Server. Tap exit to quit");

            // Creation of a DatagramPacket

            while(!(socket.isClosed())){
                String messageInput = console.readLine("Input: ");
                if (messageInput.equals("exit")) {
                    socket.close();
                    break;
                }
                byte[] data = messageInput.getBytes(StandardCharsets.UTF_8); //format needed: UTF-8
                InetAddress address = InetAddress.getByName(ServerHost);
                DatagramPacket packet = new DatagramPacket(data, data.length, address, ServerPort);
                socket.send(packet);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            client = new UDPClient(args[0],args[1]);
        } else {
            client = new UDPClient();
        }
        client.launch();
    }

}
