package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class UDPServer {

    private int port;
    private DatagramSocket socket;
    private static int defaulftPort = 8080;
    private static int max_size = 1500; // MTU default value for Ethernet packet

    // Constructor with one argument
    public UDPServer(String listening_port){
        this.port = Integer.parseInt(listening_port);
        if (port < 1024){
            System.out.println("Sudo needed, please use a port that is not reserved. We will put the default port 8080 instead.");
            port = defaulftPort;
        }
    }

    /** Default constructor of the UDP Server.  */
    public UDPServer(){
        this.port = defaulftPort; // Default port number
        System.out.println("The server will be opened with the default port number 8080. Normal usage: ");
    }


    public void launch() throws IOException {

        try {
            socket = new DatagramSocket(this.port);
            if (socket.getInetAddress() == null){
                System.out.println("Socket opened at all addresses with Port: " + socket.getLocalPort());
            }
            else{
                System.out.println("Socket opened at address: "+ socket.getInetAddress() + " Port: " + socket.getLocalPort());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Creation of a DatagramPacket

        while(!(socket.isClosed())){
            byte[] dataReceived = new byte[max_size];
            DatagramPacket packet = new DatagramPacket(dataReceived,dataReceived.length);
            System.out.println("Waiting to receive packets.");
            socket.receive(packet);
            System.out.println("Packet received");
            InetAddress source_address = packet.getAddress();
            dataReceived = packet.getData();
            System.out.println("["+source_address.toString()+"] "+ Arrays.toString(dataReceived)); // Format asked
        }

    }

    @Override
    public String toString() {
        if (socket == null){
            return "Socket is not created yet.";
        }
        else if (socket.isBound()){
            return "Socket is bound to an address.";
        }
        else if (socket.isConnected()){
            return "Socket is connected.";
        }
            return "Socket closed.";

    }
}
