package UDP;

import java.io.IOException;

public class Main {
    // First argument : Listening port
    public static void main(String[] args) throws IOException {
        if  (args.length >0){
            UDP.UDPServer server = new UDPServer(args[0]);
           server.launch();
            if (args.length > 2) {
                UDP.UDPClient client = new UDPClient(args[1],args[2]);
                client.launch();
            }
        }
        else{
            UDP.UDPServer server = new UDPServer();
            server.launch();
        }
        // To test server connectivity, use nc -zvu 127.0.0.1 [Port]

    }
}
