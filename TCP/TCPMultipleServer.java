package TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPMultipleServer {

    private int port;
    private ServerSocket serverSocket;
    private static final int defaultPort = 8080;
    private static final int max_size = 1500; // MTU default value for Ethernet packet

    // Constructor with one argument
    public TCPMultipleServer(String listening_port) {
        this.port = Integer.parseInt(listening_port);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = defaultPort;
        }
    }

    // Default Constructor
    public TCPMultipleServer() {
        this.port = defaultPort;
        System.out.println("The TCP server started at the default port " + port);
    }


    public void launch() {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("TCP server launched at port " + this.port);

            // Waiting loop for connections
            Socket clientSocket = null;

            while (true) {
                    System.out.println("Waiting for a connection...");
                    clientSocket = serverSocket.accept();
                    ConnectionThread connectionThread = new ConnectionThread(clientSocket);
                    Thread thread = new Thread(connectionThread);
                    thread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        if (serverSocket == null) {
            return "Socket is not created yet.";
        } else if (serverSocket.isBound()) {
            return "Socket is bound to an address.";
        }
        return "Socket closed.";

    }

    // Méthode main pour lancer le serveur
    public static void main(String[] args) {
        TCPMultipleServer server;
        if (args.length > 0) {
            server = new TCPMultipleServer(args[0]);
        } else {
            server = new TCPMultipleServer();
        }
        server.launch();
    }
}

