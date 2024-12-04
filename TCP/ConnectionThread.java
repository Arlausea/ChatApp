package TCP;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A Thread implementation for managing threads with multiple clients.
 * @see TCPMultipleServer
 */
public class ConnectionThread extends Thread {

    private final Socket socket;
//    private List<Socket> clients = new ArrayList<>();


    /**
     * Constructs a ConnectionThread with a given client socket.
     */
    public ConnectionThread(Socket clientSocket) {
        this.socket = clientSocket;
    }


    /**
     * The run method of ConnectionThread. Directly launched at creation of the object.
     * <p>
     *
     * Opens a socket and waits for a connection. When a client start a connection,
     * then the server start a thread with the client socket to receive and send back messages.
     * The connection is finished if the client ends its process (with exit or CTRL+D input)
     * </p>
     *
     * @throws IOException if an error occurs while creating threads.
     */
    @Override
    public void run() {
        while (true) {
            System.out.println("Waiting for a message...");
            System.out.println("Client connected from: " + socket.getInetAddress());
            // In and Out streams
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

                // Reading of data's client
                String clientMessage = in.readLine();
                System.out.println("Received message from client: " + clientMessage);

                // Response  to one thread
                out.println("Echo : " + clientMessage);


                // Closing of the connection
                if (clientMessage != null) {
                    if (clientMessage.equalsIgnoreCase("exit") | clientMessage.equals("null")) {
                        socket.close();
                        System.out.println("End of connection.");
//                        socket = null;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }




    public static void main(String[] args) {
        ConnectionThread Thread;
    }
}
