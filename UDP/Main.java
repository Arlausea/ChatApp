package UDP;

import java.io.IOException;

public class Main {
    // First argument : Listening port
    public static void main(String[] args) throws IOException {
        if  (args.length >0){
            UDP.UDPServer server = new UDPServer(args[0]);
        }
        else{
            UDP.UDPServer server = new UDPServer();
        }
        UDP.UDPServer server = new UDPServer(args[0]);
//        System.out.println(server);
        server.launch();
    }
}
