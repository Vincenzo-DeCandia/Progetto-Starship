package org.sship.starship.JSONHandler;

import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * The JSONHandler class facilitates the conversion between JSON objects and DatagramPackets.
 * It provides methods to convert JSON objects to DatagramPackets and vice versa.
 */
public class JSONHandler {
    private DatagramPacket datagramPacket;
    private JSONObject jsonObject;

    /**
     * Constructs a JSONHandler object with the given JSONObject, address, and server port.
     *
     * @param jsonObject The JSON object to be sent
     * @param address    The destination address
     * @param serverPort The destination server port
     */
    public JSONHandler(JSONObject jsonObject, InetAddress address, int serverPort) {
        this.jsonObject = jsonObject;
        toDatagramPacket(address, serverPort);
    }

    /**
     * Constructs a JSONHandler object with the given DatagramPacket.
     *
     * @param datagramPacket The DatagramPacket to be converted to a JSONObject
     */
    public JSONHandler(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
        toJSON(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
    }

    private void toDatagramPacket(InetAddress address, int serverPort) {
        String stringJSON = jsonObject.toString();
        byte[] bytes = stringJSON.getBytes();
        datagramPacket = new DatagramPacket(bytes, 0, bytes.length, address, serverPort);
    }

    private void toJSON(String stringDatagramPacket) {
        jsonObject = new JSONObject(stringDatagramPacket);
    }

    /**
     * Returns the DatagramPacket associated with this JSONHandler.
     *
     * @return The DatagramPacket
     */
    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    /**
     * Returns the JSONObject associated with this JSONHandler.
     *
     * @return The JSONObject
     */
    public JSONObject getJsonObject() {
        return jsonObject;
    }
}
