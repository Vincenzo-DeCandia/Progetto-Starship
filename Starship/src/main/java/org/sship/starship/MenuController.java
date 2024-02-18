package org.sship.starship;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * The MenuController class controls the menu UI and handles user interactions.
 */
public class MenuController {
    private SceneController sceneController; // The SceneController instance for managing scene transitions

    /**
     * Initializes the MenuController with the specified SceneController.
     *
     * @param sceneController The SceneController instance for managing scene transitions
     */
    public void init(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @FXML
    private Button play, close; // Buttons for play and close actions

    @FXML
    private TextField addressField; // Text field for entering the game address

    private String address; // The address for accessing the game

    /**
     * Handles the event when the "Play" button is clicked.
     *
     * @throws IOException            If an I/O error occurs while loading the game scene
     * @throws ClassNotFoundException If the specified class cannot be found during deserialization
     */
    @FXML
    protected void playGame() throws IOException, ClassNotFoundException {
        setAddress();
        sceneController.switchGame(address);
    }

    /**
     * Handles the event when the "Close" button is clicked.
     */
    @FXML
    protected void onCloseButtonClick() {
        Platform.exit();
    }

    /**
     * Sets the address obtained from the address field.
     */
    protected void setAddress() {
        address = addressField.getText();
    }
}
