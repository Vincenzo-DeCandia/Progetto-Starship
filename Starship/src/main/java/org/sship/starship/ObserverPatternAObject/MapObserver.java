package org.sship.starship.ObserverPatternAObject;

import org.sship.starship.MapController;
import org.sship.starship.player.AObject;

import java.util.List;

/**
 * The MapObserver class is an observer implementation that observes changes
 * in lists of AObject instances and updates the MapController accordingly.
 */
public class MapObserver implements ObserverAObject {
    private final MapController mapController;

    /**
     * Constructs a MapObserver with the specified MapController.
     *
     * @param mapController The MapController to be observed
     */
    public MapObserver(MapController mapController) {
        this.mapController = mapController;
    }

    /**
     * Updates the MapController with the latest list of AObject instances.
     *
     * @param aObjects The list of AObject instances
     */
    @Override
    public void update(List<AObject> aObjects) {
        mapController.updateEnemy(aObjects);
    }
}
