package org.sship.starship.ClientController;

import org.sship.starship.JSONHandler.JSONHandler;
import org.sship.starship.ObserverPatternAObject.EnemySubject;
import org.sship.starship.player.AObject;
import org.sship.starship.player.Enemy;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.json.JSONObject;

/**
 * The UDPClient class represents a UDP client used to communicate with the server.
 */
public class UDPClient {
    private final DatagramSocket socket;
    private final ReceiverThread receiverThread;
    private final SenderThread senderThread;

    /**
     * Constructs a UDPClient object with the specified server hostname, server port, and enemy subject.
     *
     * @param serverAddress The hostname of the server
     * @param serverPort    The port number of the server
     * @param enemySubject  The EnemySubject instance for managing enemy objects
     * @throws SocketException     If an error occurs while creating the DatagramSocket
     * @throws UnknownHostException If the hostname is invalid
     */
    public UDPClient(String serverAddress, int serverPort, EnemySubject enemySubject) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        receiverThread = new ReceiverThread(socket, enemySubject);
        senderThread = new SenderThread(socket, InetAddress.getByName(serverAddress), serverPort);
        System.out.println(serverAddress+" "+InetAddress.getByName(serverAddress));
    }

    /**
     * Initializes the client by starting sender and receiver threads.
     *
     */
    public void init() {
        senderThread.start();
        receiverThread.start();
    }

    /**
     * Sends a close message to the server.
     */
    public void sendCloseMessage() {
        senderThread.sendCloseMessage();
    }

    /**
     * Stops the sender thread.
     */
    public void stopSenderThread() {
        senderThread.interrupt();
    }

    /**
     * Stops the receiver thread.
     */
    public void stopReceiverThread() {
        receiverThread.interrupt();
    }

    /**
     * Closes the socket used by the client.
     */
    public void closeSocket() {
        socket.close();
    }
}

