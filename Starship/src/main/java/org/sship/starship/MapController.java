package org.sship.starship;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.sship.starship.commandpattern.MoveDownCommand;
import org.sship.starship.commandpattern.MoveRightCommand;
import org.sship.starship.commandpattern.MovementInvoker;
import org.sship.starship.observerPatternNodeMap.BoundaryMap;
import org.sship.starship.observerPatternNodeMap.CollisionNode;
import org.sship.starship.observerPatternNodeMap.HashMapSubject;
import org.sship.starship.player.AObject;
import org.sship.starship.player.Enemy;
import org.sship.starship.player.Starship;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MapController class manages the game map and related UI elements.
 * It handles the rendering of the game grid, player's ship, enemies, alerts, and menus.
 */
public class MapController {
    // Observers for collision detection and boundary checking
    private BoundaryMap boundaryMap;
    private CollisionNode collisionNode;
    private HashMapSubject hashMapObserved;

    private Text text = new Text(); // Text for displaying alerts
    private StackPane box = new StackPane(); // StackPane for containing the alert text

    private StackPane menu = new StackPane(); // StackPane for displaying menus

    private Button resumeButton = new Button("Riprendi"); // Button for resuming the game
    private Button quitButton = new Button("Quit"); // Button for quitting the game

    private static final int NUM_ROWS = 40;
    private static final int NUM_COLS = 40;

    private HashMap<Node, AObject> nodeHashMap = new HashMap<>(); // HashMap to store nodes and associated objects
    private AObject starship; // The player's starship object

    private GridPane gridPane = new GridPane(); // GridPane for displaying the game grid

    @FXML
    private StackPane stackPane; // StackPane for containing UI elements

    /**
     * Initializes the MapController with the specified TimeController and starship object.
     *
     * @param timeController The TimeController instance for managing game time
     * @param starship       The player's starship object
     */
    public void init(TimeController timeController, AObject starship) {
        setStarship(starship);
        createGrid();
        initObserver(timeController);
        createAlert();
        createMenu();
    }

    // Getters for buttons
    public Button getResumeButton() {
        return resumeButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    // Creates the menu UI
    private void createMenu() {
        // Set styles and properties for buttons
        resumeButton.setStyle("-fx-background-color: #CCCCCC;");
        quitButton.setStyle("-fx-background-color: #CCCCCC;");
        resumeButton.setFont(Font.font("Arial", 12));
        resumeButton.setText("Resume");

        resumeButton.setTranslateY(-40);

        menu.setPrefSize(200, 100);
        menu.setStyle("-fx-background-color: linear-gradient(to bottom, #000033, #000066);");
        menu.getChildren().addAll(resumeButton, quitButton);
    }

    /**
     * Adds the menu to the user interface.
     */
    public void addMenu() {
        stackPane.getChildren().remove(gridPane);
        stackPane.getChildren().add(menu);
    }

    /**
     * Closes the menu in the user interface.
     */
    public void closeMenu() {
        stackPane.getChildren().remove(menu);
    }

    /**
     * Creates the user interface for displaying alerts.
     */
    private void createAlert() {
        this.text.setFont(Font.font("Arial", 12));

        // Create the box for containing the text
        box.getChildren().add(this.text);
        box.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(5), null)));
        box.setMaxWidth(50);
        box.setMaxHeight(25);
    }

    // Initializes the observers for collision detection and boundary checking
    /**
     * Initializes the observers for collision detection and boundary checking.
     *
     * @param timeController The TimeController instance for managing game time
     */
    private void initObserver(TimeController timeController) {
        boundaryMap = new BoundaryMap();
        collisionNode = new CollisionNode(timeController,this, (Starship) starship);
        hashMapObserved = new HashMapSubject();

        // Register observers
        hashMapObserved.registerObserver(boundaryMap);
        hashMapObserved.registerObserver(collisionNode);
    }

    /**
     * Creates the game grid in the user interface.
     */
    public void createGrid() {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(2);
        gridPane.setVgap(2);

        // Populate the grid with cells
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Rectangle cell = new Rectangle(20, 20); // Cell dimensions
                cell.setFill(Color.BLACK);
                cell.setStroke(Color.BLACK);
                gridPane.add(cell, col, row);
            }
        }
        stackPane.setStyle("-fx-background-color: linear-gradient(to bottom, #000033, #000066);");
        stackPane.getChildren().add(gridPane);
    }

    /**
     * Sets the player's starship object.
     *
     * @param starship The player's starship object
     */
    private void setStarship(AObject starship) {
        this.starship = starship;
    }

    /**
     * Updates the position of the player's starship on the grid.
     *
     * @param starship The player's starship object
     */
    private void updateStarship(Starship starship) {
        URL imageURL = getClass().getResource("sprite/images.jpeg");
        ImageView imageView = new ImageView(imageURL.toExternalForm());
        imageView.setFitWidth(20); // Set image width
        imageView.setFitHeight(20); // Set image height
        gridPane.add(imageView, starship.getPositionX(), starship.getPositionY());
    }

    /**
     * Displays an alert with the specified text.
     *
     * @param text The text to display in the alert
     */
    public void changeAlert(String text) {
        if (stackPane.getChildren().contains(box)) {
            removeAlert();
        }
        this.text.setText(text);
        stackPane.getChildren().add(this.box);
    }

    /**
     * Updates the game map in the user interface.
     */
    public void updateMap() {
        stackPane.getChildren().clear();
        gridPane.getChildren().clear();

        createGrid();

        for (Map.Entry<Node, AObject> enemy : nodeHashMap.entrySet()) {
            Node node = enemy.getKey();
            AObject aObject = enemy.getValue();
            gridPane.add(node, aObject.getPositionX(), aObject.getPositionY());
        }

        handlerObserver();

        updateStarship((Starship) starship);
    }

    /**
     * Handles the observers
     */
    private void handlerObserver() {
        hashMapObserved.setStateNodeList(nodeHashMap);
    }

    /**
     * Updates the position of the enemies on the game grid.
     */
    public void updateEnemyPosition() {
        MovementInvoker movementInvoker = new MovementInvoker();

        for (Map.Entry<Node, AObject> enemy : nodeHashMap.entrySet()) {
            Node node = enemy.getKey();
            AObject aObject = enemy.getValue();
            System.out.println(aObject.getPositionX());

            MoveDownCommand toDown = new MoveDownCommand(aObject);
            movementInvoker.setCommand(toDown);
            movementInvoker.invokeCommand();
        }
    }

    /**
     * Updates the list of enemies on the game grid.
     *
     * @param enemyList The list of enemies to update
     */
    public void updateEnemy(List<AObject> enemyList) {

        for (AObject enemy : enemyList) {
            URL imageURL = getClass().getResource("sprite/enemy.jpg");
            ImageView imageView = new ImageView(imageURL.toExternalForm());
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            nodeHashMap.put(imageView, enemy);
        }
    }

    /**
     * Removes a node from the game grid.
     *
     * @param node The node to remove
     */
    public void removeNode(Node node) {
        gridPane.getChildren().remove(node);
        nodeHashMap.remove(node);
    }

    /**
     * Returns the list of nodes to update on the game grid.
     *
     * @return The list of nodes to update
     */
    public List<Node> getListNodeUpdate() {
        return gridPane.getChildren();
    }

    /**
     * Removes the alert from the user interface.
     */
    public void removeAlert() {
        stackPane.getChildren().remove(box);
    }
}
