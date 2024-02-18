package org.sship.starship.ClientController;

import org.json.JSONObject;
import org.sship.starship.JSONHandler.JSONHandler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Stack;

/**
 * The SenderThread class represents a thread responsible for sending JSON data packets to the server.
 */
public class SenderThread extends Thread {
    private final DatagramSocket socket;
    private final Stack<JSONObject> jsonObjectStack = new Stack<>();
    private final InetAddress inetAddress;
    private final int serverPort;

    /**
     * Constructs a SenderThread object with the specified DatagramSocket, server address, and port.
     *
     * @param socket      The DatagramSocket to use for sending packets
     * @param inetAddress The InetAddress of the server
     * @param port        The port number of the server
     */
    public SenderThread(DatagramSocket socket, InetAddress inetAddress, int port) {
        this.inetAddress = inetAddress;
        serverPort = port;
        this.socket = socket;

        JSONObject dimensionMap = new JSONObject();
        dimensionMap.put("message", "mapDimension");
        dimensionMap.put("width", 40);
        dimensionMap.put("height", 40);

        pushJSONObject(dimensionMap);
    }

    /**
     * Sends a close message to the server indicating the client's intention to close the connection.
     */
    public void sendCloseMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "close");

        pushJSONObject(jsonObject);
    }

    /**
     * Checks if there is any data waiting to be sent.
     *
     * @return true if there is data to be sent, false otherwise
     */
    public synchronized boolean thereIsDataToSend() {
        return !jsonObjectStack.isEmpty();
    }

    /**
     * Pushes a JSONObject onto the stack for sending to the server.
     *
     * @param jsonObject The JSONObject to be sent
     */
    public synchronized void pushJSONObject(JSONObject jsonObject) {
        jsonObjectStack.push(jsonObject);
    }

    /**
     * Retrieves and removes the next JSONObject from the stack.
     *
     * @return The next JSONObject to be sent
     */
    public synchronized JSONObject getJSON() {
        return jsonObjectStack.pop();
    }

    /**
     * Starts the sender thread to send JSON data packets to the server.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                if (thereIsDataToSend()) {
                    JSONObject jsonObject;
                    jsonObject = getJSON();
                    DatagramPacket datagramPacket = new JSONHandler(jsonObject, inetAddress, serverPort).getDatagramPacket();
                    socket.send(datagramPacket);
                }
            }
        } catch (IOException e) {
            System.err.println("Error while sending data: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
