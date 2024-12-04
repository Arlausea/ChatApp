package TCP;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionThread extends Thread {

    private final Socket socket;
//    private List<Socket> clients = new ArrayList<>();

    public ConnectionThread(Socket clientSocket) {
        this.socket = clientSocket;
    }



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

                // Answer to one thread
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
