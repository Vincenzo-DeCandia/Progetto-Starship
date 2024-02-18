package org.sship.starship.observerPatternNodeMap;

import javafx.scene.Node;
import org.sship.starship.MapController;
import org.sship.starship.TimeController;
import org.sship.starship.player.AObject;
import org.sship.starship.player.Starship;

import java.util.HashMap;

/**
 * The CollisionNode class represents an observer in the observer pattern
 * responsible for detecting collisions between objects in the game.
 */
public class CollisionNode implements ObserverHashMap {
    private HashMap<Node, AObject> hashMap;
    private MapController mapController;
    private Starship starship;
    private TimeController timeController;

    /**
     * Constructs a CollisionNode with the specified TimeController, MapController, and Starship.
     *
     * @param timeController The TimeController instance for managing game time
     * @param mapController  The MapController instance for managing the game map
     * @param starship       The player's starship object
     */
    public CollisionNode(TimeController timeController, MapController mapController, Starship starship) {
        this.mapController = mapController;
        this.starship = starship;
        this.timeController = timeController;
    }

    /**
     * Updates the CollisionNode with the latest HashMap of objects.
     *
     * @param hashMap The HashMap of objects
     */
    @Override
    public void update(HashMap<Node, AObject> hashMap) {
        this.hashMap = hashMap;
        checkCollision(hashMap);
    }

    /**
     * Checks for collisions between the starship and other objects.
     *
     * @param hashMap The HashMap of objects to check for collisions
     */
    public void checkCollision(HashMap<Node, AObject> hashMap) {
        for (AObject aObject : hashMap.values()) {
            if (starship.getPositionX() == aObject.getPositionX() && starship.getPositionY() == aObject.getPositionY()) {
                mapController.changeAlert("Attenzione collisione");
                timeController.stopTimelineEnemyPosition();
                timeController.stopAnimation();
                timeController.startTimerCollision();
                break; // Exit loop after detecting one collision
            }
        }
    }
}
