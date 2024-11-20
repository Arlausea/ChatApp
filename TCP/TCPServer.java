package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    private int port;
    private ServerSocket serverSocket;
    private static final int defaultPort = 8080;
    private static final int max_size = 1500; // MTU default value for Ethernet packet

    // Constructor with one argument
    public TCPServer(String listening_port) {
        this.port = Integer.parseInt(listening_port);
        if (port < 1024) {
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = defaultPort;
        }
    }

    // Default Constructor
    public TCPServer() {
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
//                else{
//                    clientSocket = null;
//                    System.out.println("Client lost.");
//                    }
//                }
            }





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        if (serverSocket == null) {
            return "Serveur non initialisé.";
        } else if (serverSocket.isBound()) {
            return "Serveur TCP lié à un port.";
        } else {
            return "Serveur TCP fermé.";
        }
    }

    // Méthode main pour lancer le serveur
    public static void main(String[] args) {
        TCPServer server;
        if (args.length > 0) {
            server = new TCPServer(args[0]);
        } else {
            server = new TCPServer();
        }
        server.launch();
    }
}

