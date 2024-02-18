package org.sship.starship;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.sship.starship.ClientController.UDPClient;
import org.sship.starship.ObserverPatternAObject.EnemySubject;
import org.sship.starship.ObserverPatternAObject.MapObserver;
import org.sship.starship.commandpattern.MoveLeftCommand;
import org.sship.starship.commandpattern.MoveRightCommand;
import org.sship.starship.commandpattern.MovementInvoker;
import org.sship.starship.player.Starship;

import java.io.IOException;
import java.util.List;

/**
 * The GameController class manages the game logic in the Starship application.
 * It controls player input, updates the game state, and communicates with the server.
 */
public class GameController {
    private SceneController sceneController; // Manages scene transitions
    private TimeController timeController; // Manages game time
    private MapController mapController; // Manages the game map
    private Starship starship = new Starship(); // The player's starship

    private String address;

    private List<Node> Enemy; // List of enemy nodes

    public GameController(String address) {
        this.address = address;
    }

    /**
     * Initializes the GameController with the specified SceneController and MapController.
     * This method sets up the game environment, initializes game components, and starts the game.
     *
     * @param sceneController The SceneController instance for managing scenes
     * @param mapController   The MapController instance for managing the game map
     * @throws IOException If an I/O error occurs during initialization
     * @throws ClassNotFoundException If the class is not found during deserialization
     */
    public void init(SceneController sceneController, MapController mapController) throws IOException, ClassNotFoundException {
        this.sceneController = sceneController;
        this.mapController = mapController;
        this.timeController = new TimeController(mapController, this); // Initialize TimeController

        // Initialize the mapController with TimeController and starship reference
        mapController.init(timeController, starship);

        // Handle player input
        Scene scene = sceneController.getScene();
        scene.setOnKeyPressed(event -> {
            if (timeController.isTimerCollisionRunning() && !starship.isDead()) {
                mapController.removeAlert();
                timeController.stopTimerCollision();
            }
            if (event.getCode() == KeyCode.LEFT) {
                movementInvoker.setCommand(toLeft);
                movementInvoker.invokeCommand();
            } else if (event.getCode() == KeyCode.RIGHT) {
                movementInvoker.setCommand(toRight);
                movementInvoker.invokeCommand();
            } else if (event.getCode() == KeyCode.P) {
                mapController.addMenu();
                timeController.stopAnimation();
            }
        });

        // Quit button event handler
        mapController.getQuitButton().setOnAction(actionEvent -> {
            try {
                client.sendCloseMessage();
                Thread.sleep(500);
                client.stopSenderThread();
                client.stopReceiverThread();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                client.closeSocket();
            }
            System.exit(0);
        });

        // Resume button event handler
        mapController.getResumeButton().setOnAction(actionEvent -> {
            mapController.closeMenu();
            timeController.startAnimation();
        });

        // Initialize the observer and register it to the subject
        mapObserver = new MapObserver(mapController);
        enemySubject.registerObserver(mapObserver);

        // Initialize the UDP client for communication with the server
        client = new UDPClient(address, 5000, enemySubject);
        client.init();

        // Start game animation and timeline
        timeController.startAnimation();
        timeController.startTimelineEnemyPosition();
    }

    // Movement commands for the starship
    private MoveLeftCommand toLeft = new MoveLeftCommand(starship);
    private MoveRightCommand toRight = new MoveRightCommand(starship);
    private MovementInvoker movementInvoker = new MovementInvoker();

    // Subject and observer for managing enemies on the map
    private EnemySubject enemySubject = new EnemySubject();
    private MapObserver mapObserver;

    // UDP client for communication with the server
    private UDPClient client;

    /**
     * Handles the game over condition.
     * This method sets the player's starship as dead, displays a game over message,
     * and sends a close message to the server.
     */
    public void gameOver() {
        starship.setDead(true);
        mapController.changeAlert("GAME OVER");
        try {
            client.sendCloseMessage();
            Thread.sleep(500);
            client.closeSocket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


