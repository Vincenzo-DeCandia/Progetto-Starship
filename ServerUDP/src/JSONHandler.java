import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * The JSONHandler class is responsible for handling JSON objects and DatagramPackets.
 * It converts JSON objects to DatagramPackets for sending over a network, and vice versa.
 */
public class JSONHandler {
    private String stringDatagram; // String representation of the DatagramPacket data
    private JSONObject jsonObject; // JSON object extracted from the DatagramPacket
    private DatagramPacket datagramPacket; // DatagramPacket containing JSON data

    /**
     * Constructs a JSONHandler object with the specified JSON object, address, and port.
     * Converts the JSON object to bytes and constructs a DatagramPacket with the specified address and port.
     *
     * @param jsonObject The JSON object to be sent
     * @param address    The destination address
     * @param port       The destination port
     */
    public JSONHandler(JSONObject jsonObject, InetAddress address, int port) {
        String stringJSON = jsonObject.toString();
        byte[] bytes = stringJSON.getBytes();
        // Construct a DatagramPacket with the JSON data, address, and port
        datagramPacket = new DatagramPacket(bytes, 0, bytes.length, address, port);
    }

    /**
     * Constructs a JSONHandler object with the specified DatagramPacket.
     * Extracts the JSON data from the DatagramPacket and constructs a JSON object.
     *
     * @param datagramPacket The DatagramPacket containing JSON data
     */
    public JSONHandler(DatagramPacket datagramPacket) {
        // Extract the JSON data from the DatagramPacket
        this.stringDatagram = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        toJSON();
    }

    // Converts the JSON string to a JSON object
    private void toJSON() {
        jsonObject = new JSONObject(stringDatagram);
    }

    /**
     * Returns the DatagramPacket constructed by the JSONHandler.
     *
     * @return The DatagramPacket containing JSON data
     */
    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    /**
     * Returns the JSON object extracted from the DatagramPacket.
     *
     * @return The JSON object extracted from the DatagramPacket
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }
}