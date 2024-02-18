package org.sship.starship;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The SceneController class manages the switching of scenes in the Starship application.
 */
public class SceneController {
    private Stage stage; // The primary stage of the application
    private Parent root; // The root node of the scene
    private Scene scene; // The current scene

    private FXMLLoader loader; // Loader for loading FXML files

    /**
     * Gets the current scene.
     *
     * @return The current scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the primary stage of the application.
     *
     * @param stage The primary stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Switches the scene to the specified FXML file.
     *
     * @param nameScene The name of the FXML file representing the scene
     * @throws IOException If an I/O error occurs while loading the FXML file
     */
    @FXML
    private void switchScene(String nameScene) throws IOException {
        loader = new FXMLLoader(getClass().getResource(nameScene));
        root = loader.load();
        Scene scene = new Scene(root);
        this.scene = scene;

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the scene to the menu scene.
     *
     * @throws IOException If an I/O error occurs while loading the menu scene
     */
    public void switchMenu() throws IOException {
        switchScene("menu.fxml");
        MenuController menuController = loader.getController();
        menuController.init(this);
    }

    /**
     * Switches the scene to the game scene.
     *
     * @param address The address of the game
     * @throws IOException            If an I/O error occurs while loading the game scene
     * @throws ClassNotFoundException If the game controller class is not found
     */
    public void switchGame(String address) throws IOException, ClassNotFoundException {
        switchScene("game.fxml");
        GameController gameController = new GameController(address);
        MapController mapController = loader.getController();
        gameController.init(this, mapController);
    }
}

