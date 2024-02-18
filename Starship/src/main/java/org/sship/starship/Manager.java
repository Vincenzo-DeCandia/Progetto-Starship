package org.sship.starship;

import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Manager class represents the entry point of the application.
 * It extends the javafx.application.Application class and is responsible for initializing the JavaFX environment.
 */
public class Manager extends javafx.application.Application {

    // Instance of SceneController responsible for managing scenes and stages
    private final SceneController sceneController = new SceneController();

    /**
     * The start method is called when the JavaFX application is launched.
     * It sets up the primary stage and switches to the initial scene (menu).
     *
     * @param stage The primary stage of the application
     * @throws IOException If an I/O error occurs while loading the scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Set the primary stage for the SceneController
        sceneController.setStage(stage);

        // Switch to the initial scene (menu)
        sceneController.switchMenu();
    }

    /**
     * The main method is the entry point of the Java application.
     * It launches the JavaFX application by calling the launch method.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}
