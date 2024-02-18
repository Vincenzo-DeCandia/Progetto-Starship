package org.sship.starship.ObserverPatternAObject;

import org.sship.starship.player.AObject;

import java.util.List;

/**
 * The ObserverAObject interface represents an observer in the observer pattern
 * for observing changes in lists of AObject instances.
 */
public interface ObserverAObject {
    /**
     * Updates the observer with the latest list of AObject instances.
     *
     * @param aObjects The list of AObject instances
     */
    void update(List<AObject> aObjects);
}
