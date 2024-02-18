package org.sship.starship.observerPatternNodeMap;

import javafx.scene.Node;
import org.sship.starship.player.AObject;

import java.util.HashMap;

/**
 * The ObserverHashMap interface defines the contract for objects that observe changes in a HashMap.
 */
public interface ObserverHashMap {
    /**
     * Updates the observer with the latest state of the HashMap.
     *
     * @param hashMap The updated HashMap containing nodes and associated objects
     */
    public void update(HashMap<Node, AObject> hashMap);
}
