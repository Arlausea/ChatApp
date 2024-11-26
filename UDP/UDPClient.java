package UDP;

import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient {

    private final int ServerPort;
    private final String ServerHost;

    public UDPClient(String host, String port) {
        this.ServerHost = host;
        this.ServerPort = Integer.parseInt(port);

    }

    /** Default constructor of the UDP Server.  */
    public UDPClient() {
        this.ServerPort = 8080; // Default port number
        this.ServerHost = "localhost"; // Default Host
        System.out.println("The server will be opened with the default port number 8080 and host. Normal usage: java Main [Listening Port].");
    }

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
