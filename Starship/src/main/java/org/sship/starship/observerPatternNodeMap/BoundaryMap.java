package org.sship.starship.observerPatternNodeMap;

import javafx.scene.Node;
import org.sship.starship.player.AObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The BoundaryMap class represents an observer in the observer pattern
 * responsible for removing objects that move out of the game boundaries.
 */
public class BoundaryMap implements ObserverHashMap {
    private HashMap<Node, AObject> hashMap;

    /**
     * Constructs a BoundaryMap instance.
     */
    public BoundaryMap() {
        // Constructor
    }

    /**
     * Updates the BoundaryMap with the latest HashMap of objects.
     *
     * @param hashMap The HashMap of objects
     */
    @Override
    public void update(HashMap<Node, AObject> hashMap) {
        this.hashMap = hashMap;
        removeNode();
    }

    /**
     * Removes objects that move out of the game boundaries.
     */
    public void removeNode() {
        Iterator<Map.Entry<Node, AObject>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Node, AObject> enemy = iterator.next();
            Node node = enemy.getKey();
            AObject aObject = enemy.getValue();
            if (aObject.getPositionY() > 39 || aObject.getPositionX() < 0 || aObject.getPositionX() > 39) {
                iterator.remove(); // Remove the element using the iterator
                node = null;
                aObject = null;
            }
        }
    }
}
