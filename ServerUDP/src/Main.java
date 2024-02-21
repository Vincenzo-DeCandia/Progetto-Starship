import java.io.IOException;

/**
 * The Main class serves as the entry point of the application.
 * It initializes and starts the UDP server to handle incoming UDP packets.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     * It creates a new UDPServer instance listening on port 5000 and starts the server.
     *
     * @param args The command-line arguments (not used)
     * @throws IOException If an I/O error occurs while starting the server
     */
    public static void main(String[] args) throws IOException {
        // Create a new UDPServer instance listening on port 5000
        UDPServer udpServer = new UDPServer(5000);

        // Add a shutdown hook to handle clean socket closure
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            udpServer.closeSocket(); // Close the socket before terminating the server
        }));
        
        // Start the UDP server
        udpServer.start();
    }
}
