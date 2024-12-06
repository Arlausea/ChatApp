package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * A UDP Client implementation for sending messages to a UDP Server.
 * The client reads messages from the console, sends them to a UDP server,
 * and closes the connection when the user types "exit" or CTRL+D.
 * @see TCPServer
 */
public class TCPClient {

    private final String serverHost;
    private final int serverPort;


    /**
     * Constructs a TCPClient with the specified server hostname and port.
     *
     * @param host the hostname or IP address of the server
     * @param port the port number of the server
     */
    public TCPClient(String host, String port) {
        this.serverHost = host;
        this.serverPort = Integer.parseInt(port);

    }

    /**
     * Constructs a TCPClient with a defined number port (8080).
     */
    public TCPClient() {
        this.serverHost = "localhost";
        this.serverPort = 8080;
        System.out.println("The client will connect to localhost:8080. Normal usage : java TCPClient <host> <port>");
    }

    /**
     * Handles the communication with the server.
     *
     * @param consoleInput the BufferedReader for reading user input
     * @param in the BufferedReader for reading server responses
     * @param out the PrintWriter for sending messages to the server
     * @throws IOException if an I/O error occurs
     */
    private void handleCommunication(BufferedReader consoleInput, BufferedReader in, PrintWriter out) throws IOException {
        String message;
        System.out.println("Tap a message to chat. Tap exit or CTRL+D to quit.");
        while (true) {
            System.out.print("[YOU]: ");
            message = consoleInput.readLine();
            if ("exit".equalsIgnoreCase(message) | message == null )  {
                out.println(message);
                System.out.println("Disconnection...");
                break;
            }
            if (!"".equalsIgnoreCase(message)){
                out.println(message);
                String response = in.readLine();
                System.out.println("[SERVER]: " + response);
            }
        }
    }




    /**
     * The main method of TCPClient.
     * <p>
     * Opens a socket and sends messages
     * to the TCP Server. If no input is provided, the client sends nothing.
     * If the input is "exit" or CTRL+D, stops the connection and closes the socket.
     * </p>
     *
     * @throws IOException if an error occurs while sending messages.
     */
    public void launch() {
        try (Socket socket = new Socket(serverHost, serverPort)) {
            System.out.println("Connected to the server: " + serverHost + " with port " + serverPort);


            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            handleCommunication(consoleInput,in,out);



        } catch (IOException e) {
            System.out.println("Error connection: " + e.getMessage());
        }
    }

    /**
     * The main entry point for the TCPClient application.
     * <p>
     * Accepts optional arguments to specify the server host and port. If no arguments are provided, it
     * uses default values according to the default constructor.
     * </p>
     * @param args optional arguments: [host] [port]
     * @throws IOException if an error occurs while running the client
     */
    public static void main(String[] args) {
        TCPClient client;
        if (args.length > 1) {
            client = new TCPClient(args[0], args[1]);
        } else {
            client = new TCPClient();
        }
        client.launch();
    }
}