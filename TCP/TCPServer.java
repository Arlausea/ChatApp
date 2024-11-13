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

    // Constructeur avec un argument
    public TCPServer(String listening_port) {
        this.port = Integer.parseInt(listening_port);
        if (port < 1024) {
            System.out.println("Port réservé. Utilisation du port par défaut 8080.");
            port = defaultPort;
        }
    }

    // Constructeur par défaut
    public TCPServer() {
        this.port = defaultPort;
        System.out.println("Le serveur TCP va utiliser le port par défaut 8080.");
    }

    // Méthode pour lancer le serveur
    public void launch() {
        try {
            serverSocket = new ServerSocket(this.port);
            System.out.println("Serveur TCP lancé sur le port : " + this.port);

            // Boucle d'attente pour les connexions client
            while (true) {
                System.out.println("En attente de connexion...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté depuis : " + clientSocket.getInetAddress());

                // Création des flux d'entrée et de sortie
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

                // Lecture des données envoyées par le client
                String clientMessage = in.readLine();
                System.out.println("Message reçu du client : " + clientMessage);

                // Réponse au client
                out.println("Echo : " + clientMessage);

                // Fermeture de la connexion client
                clientSocket.close();
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

