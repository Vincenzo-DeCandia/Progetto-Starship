package org.sship.starship;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The TimeController class manages various timelines and timers used in the game.
 */
public class TimeController {
    private Timeline TimeEnemyPosition; // Timeline for enemy positions
    private Timeline TimerCollisionObject; // Timer for collision with an object
    private AnimationTimer animationTimer; // Timer for animation
    private boolean animationTimerRunning; // Indicates if the animation timer is running
    private boolean TimerCollisionRunning; // Indicates if the collision timer is running

    /**
     * Constructs a TimeController object with the specified MapController and GameController.
     *
     * @param mapController   The MapController instance for updating enemy positions
     * @param gameController  The GameController instance for handling game-related actions
     */
    public TimeController(MapController mapController, GameController gameController) {
        TimeEnemyPosition = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Method to execute every 1 second
            mapController.updateEnemyPosition();
        }));

        // Set the timeline to repeat indefinitely
        TimeEnemyPosition.setCycleCount(Timeline.INDEFINITE);

        TimerCollisionObject = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
            // Trigger a timer of 3 seconds on collision
            System.out.println("You are Dead");
            gameController.gameOver();
        }));

        TimerCollisionObject.setCycleCount(1);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                mapController.updateMap();
            }
        };
    }

    /**
     * Starts the collision timer and stops other animations and timelines.
     */
    public void startTimerCollision() {
        stopTimelineEnemyPosition();
        stopAnimation();
        TimerCollisionRunning = true;
        TimerCollisionObject.play();
    }

    /**
     * Stops the collision timer and restarts animations and timelines.
     */
    public void stopTimerCollision() {
        TimerCollisionObject.stop();
        TimerCollisionRunning = false;
        startAnimation();
        startTimelineEnemyPosition();
    }

    /**
     * Checks if the collision timer is currently running.
     *
     * @return true if the collision timer is running, false otherwise
     */
    public boolean isTimerCollisionRunning() {
        return TimerCollisionRunning;
    }

    /**
     * Starts the timeline for updating enemy positions.
     */
    public void startTimelineEnemyPosition() {
        TimeEnemyPosition.play();
    }

    /**
     * Stops the timeline for updating enemy positions.
     */
    public void stopTimelineEnemyPosition() {
        TimeEnemyPosition.stop();
    }

    /**
     * Starts the animation timer for updating the game map.
     */
    public void startAnimation() {
        animationTimer.start();
        animationTimerRunning = true;
    }

    /**
     * Stops the animation timer for updating the game map.
     */
    public void stopAnimation() {
        animationTimer.stop();
        animationTimerRunning = false;
    }

    /**
     * Checks if the animation timer is currently running.
     *
     * @return true if the animation timer is running, false otherwise
     */
    public boolean isAnimationTimerRunning() {
        return animationTimerRunning;
    }

}
