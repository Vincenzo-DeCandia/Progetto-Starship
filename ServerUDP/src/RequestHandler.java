import org.json.JSONObject;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/**
 * The RequestHandler class represents a thread responsible for handling requests from clients in a UDP server.
 * It receives and processes data from clients, and sends responses back to the clients.
 */
public class RequestHandler extends Thread {
    private final DatagramSocket socket; // Socket for handling UDP communication
    private final DatagramPacket receivePacket; // Packet received from the client
    private boolean running = false; // Flag indicating whether the sender thread is running
    private int dimensionWidth; // Width of the game map

    /**
     * Constructs a RequestHandler object with the received packet and socket.
     *
     * @param packet The DatagramPacket received from the client
     * @param socket The DatagramSocket for handling UDP communication
     */
    public RequestHandler(DatagramPacket packet, DatagramSocket socket) {
        this.socket = socket;
        this.receivePacket = packet;
    }

    /**
     * Runs the RequestHandler thread, receiving and processing data from clients,
     * and sending responses back to the clients.
     */
    @Override
    public void run() {
        receive(); // Receive and process data from the client
        if (running) {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Generate random positions for enemy entities
                    // Number of packets to send in the sender thread
                    int numberPacket = 5;
                    for (int i = 0; i < numberPacket; i++) {
                        Random random = new Random();
                        int randomValue = random.nextInt(getDimensionWidth());
                        JSONObject enemy = new JSONObject();
                        enemy.put("positionX", randomValue);
                        enemy.put("positionY", 0);
                        send(enemy); // Send the position of enemy to the client
                    }
                    Thread.sleep(2000); // Pause for 2 seconds before sending the next batch of positions
                }
            } catch (InterruptedException | IOException e) {       
                System.out.println("Thread interrupted");
            }
        }
    }

    /**
     * Returns the dimension width of the game map.
     *
     * @return The width of the game map
     */
    public int getDimensionWidth() {
        return dimensionWidth;
    }

    /**
     * Processes the received data from the client.
     */
    private void receive() {
        JSONObject jsonObject = new JSONHandler(receivePacket).getJsonObject();
        String typeMessage = jsonObject.getString("message");
        switch (typeMessage) {
            case "mapDimension":
                // Set the dimension width and allow the sender thread to start
                dimensionWidth = jsonObject.getInt("width");
                running = true;
                break;
            case "game over":
                // Received game over message, stop the sender thread
                System.out.println("STOP");
                running = false;
                break;
            default:
                break;
        }
    }

    /**
     * Sends data to the client.
     *
     * @param jsonObject The JSONObject to be sent to the client
     * @throws IOException If an I/O error occurs while sending the data
     */
    public void send(JSONObject jsonObject) throws IOException {
        InetAddress clientAddress = receivePacket.getAddress();
        int clientPort = receivePacket.getPort();
        DatagramPacket sendPacket = new JSONHandler(jsonObject, clientAddress, clientPort).getDatagramPacket();
        socket.send(sendPacket); // Send the packet to the client
    }
}
