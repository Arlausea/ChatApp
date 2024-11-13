package UDP;

import java.io.IOException;

public class MainClient {
    // First argument : Listening port
    public static void main(String[] args) throws IOException {

            if (args.length > 1) {
                UDPClient client = new UDPClient(args[0], args[1]);
                client.launch();
            }
            else{
                UDPClient client = new UDPClient();
                client.launch();
            }

        // To test server connectivity, use nc -zvu 127.0.0.1 [Port]

    }
}
