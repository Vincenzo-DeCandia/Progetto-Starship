import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;


/**
 * The UDPServer class represents a UDP server that listens for incoming packets from clients.
 */
public class UDPServer {
    private DatagramSocket socket;
    private final int portNumber;
    private final Map<String, Thread> clientThreads = new HashMap<>();

    /**
     * Constructs a UDPServer object with the specified port number.
     *
     * @param portNumber The port number on which the server listens for incoming packets
     */
    public UDPServer(int portNumber) {
        this.portNumber = portNumber;
    }

    /**
     * Starts the UDP server, listening for incoming packets and handling them.
     * @throws SocketException If a Socket error occurs while instantiating a new DatagramSocket
     */
    public void start() throws SocketException {

        // Create a new DatagramSocket bound to the specified port number
        socket = new DatagramSocket(portNumber);
        try {
            // Listen for incoming packets and handle them
            while (true) {
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket); // Receive a packet

                InetAddress clientAddress = receivePacket.getAddress(); // Get the client's address
                int clientPort = receivePacket.getPort();

                String clientString = clientAddress.toString() + clientPort;

                // Check if there is an existing thread associated with the client
                if (clientThreads.containsKey(clientString)) {
                    Thread previousThread = clientThreads.get(clientString);
                    if (!previousThread.isInterrupted()) {
                        previousThread.interrupt();
                    }
                    clientThreads.remove(clientString);
                }

                // Create a new RequestHandler and its associated thread for the incoming packet
                RequestHandler requestHandler = new RequestHandler(receivePacket, socket);
                // Store the thread and RequestHandler pair in the clientThreads map
                clientThreads.put(clientString, requestHandler);
                // Start the thread to handle the incoming packet
                requestHandler.start();
            }
        } catch (IOException exception) {
            System.out.println("IOException");
        }
        finally {
            socket.close();
        }
    }
    /**
     * Closes the UDP socket.
     */
    public void closeSocket() {
        socket.close();
    }
}
