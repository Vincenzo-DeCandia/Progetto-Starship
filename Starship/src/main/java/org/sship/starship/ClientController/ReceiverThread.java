package org.sship.starship.ClientController;

import org.json.JSONObject;
import org.sship.starship.JSONHandler.JSONHandler;
import org.sship.starship.ObserverPatternAObject.EnemySubject;
import org.sship.starship.player.AObject;
import org.sship.starship.player.Enemy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * The ReceiverThread class represents a thread responsible for receiving data packets from the server.
 */
public class ReceiverThread extends Thread {
    private final DatagramSocket socket;
    private final EnemySubject enemySubject;

    /**
     * Constructs a ReceiverThread object with the specified DatagramSocket and EnemySubject.
     *
     * @param socket       The DatagramSocket to use for receiving packets
     * @param enemySubject The EnemySubject instance for managing enemy objects
     */
    public ReceiverThread(DatagramSocket socket, EnemySubject enemySubject) {
        this.socket = socket;
        this.enemySubject = enemySubject;
    }

    /**
     * Runs the receiver thread to continuously receive data packets from the server.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                List<AObject> enemyList = new ArrayList<>();
                enemyList.add(receive());
                enemySubject.setStateEnemyList(enemyList);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Receiver closed");
        }
    }

    /**
     * Receives a data packet from the server and constructs an Enemy object from the received JSON data.
     *
     * @return The Enemy object received from the server
     * @throws IOException            If an I/O error occurs while receiving the packet
     * @throws ClassNotFoundException If the class of a serialized object cannot be found
     */
    public AObject receive() throws IOException, ClassNotFoundException {
        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivedPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivedPacket);
        JSONObject jsonObject = new JSONHandler(receivedPacket).getJsonObject();
        int x = jsonObject.getInt("positionX");
        int y = jsonObject.getInt("positionY");
        return new Enemy(x, y);
    }
}