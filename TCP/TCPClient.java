package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClient {

    private final String serverHost;
    private final int serverPort;

    public TCPClient(String host, String port) {
        this.serverHost = host;
        this.serverPort = Integer.parseInt(port);

    }

    // Constructor by default
    public TCPClient() {
        this.serverHost = "localhost";
        this.serverPort = 8080;
        System.out.println("The client will connect to localhost:8080. Normal usage : java TCPClient <host> <port>");
    }

    public void launch() {
        try (Socket socket = new Socket(serverHost, serverPort)) {
            System.out.println("Connected to the server: " + serverHost + " with port " + serverPort);


            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            System.out.println("Tap a message to chat. Tap exit or CTRL+D to quit.");

            while (true) {
                System.out.print("[YOU]: ");
                message = consoleInput.readLine();

                if ("exit".equalsIgnoreCase(message) | message == null ) {
                    out.println(message);
                    System.out.println("Deconnection...");
                    break;
                }
                if (!"".equalsIgnoreCase(message)) {

                    out.println(message);


                    String response = in.readLine();
                    System.out.println("[SERVER]: " + response);
                }

            }

        } catch (IOException e) {
            System.out.println("Error connection: " + e.getMessage());
        }
    }

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