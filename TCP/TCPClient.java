package UDP;

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

    // Constructeur par défaut
    public TCPClient() {
        this.serverHost = "localhost";
        this.serverPort = 8080;
        System.out.println("Le client se connectera au serveur par défaut (localhost:8080). Usage normal : java TCPClient <host> <port>");
    }

    public void launch() {
        try (Socket socket = new Socket(serverHost, serverPort)) {
            System.out.println("Connecté au serveur : " + serverHost + " sur le port " + serverPort);

            // Création des flux d'entrée et de sortie
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            System.out.println("Tapez un message pour l'envoyer au serveur. Tapez 'exit' pour quitter.");

            while (true) {
                System.out.print("Message à envoyer : ");
                message = consoleInput.readLine();

                if ("exit".equalsIgnoreCase(message) | message == null ) {
                    out.println(message);
                    System.out.println("Déconnexion...");
                    break;
                }
                if (!"".equalsIgnoreCase(message)) {
                    // Envoi du message au serveur
                    out.println(message);

                    // Lecture de la réponse du serveur
                    String response = in.readLine();
                    System.out.println("Réponse du serveur : " + response);
                }

            }

        } catch (IOException e) {
            System.out.println("Erreur de connexion au serveur : " + e.getMessage());
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