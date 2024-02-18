package org.sship.starship.observerPatternNodeMap;

import javafx.scene.Node;
import org.sship.starship.player.AObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The HashMapSubject class represents the subject in the observer pattern,
 * responsible for managing observers and notifying them of changes in a HashMap.
 */
public class HashMapSubject implements Subject {
    private ArrayList<ObserverHashMap> observerList;
    private int totalObs = 0;
    // State
    private HashMap<Node, AObject> StateHashMap;

    /**
     * Constructs a new HashMapSubject with an empty list of observers.
     */
    public HashMapSubject() {
        observerList = new ArrayList<ObserverHashMap>();
    }

    /**
     * Sets the state of the HashMap and notifies observers.
     *
     * @param hashMap The updated HashMap to set as the state
     */
    public void setStateNodeList(HashMap<Node, AObject> hashMap) {
        StateHashMap = hashMap;
        notifyObserver();
    }

    /**
     * Gets the current state of the HashMap.
     *
     * @return The current state of the HashMap
     */
    public HashMap<Node, AObject> getStateNodeList() {
        return StateHashMap;
    }

    @Override
    public void registerObserver(ObserverHashMap observer) {
        observerList.add(observer);
        totalObs++;
    }

    @Override
    public void unregisterObserver(ObserverHashMap observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Iterator<ObserverHashMap> it = observerList.iterator(); it.hasNext();) {
            ObserverHashMap observer = it.next();
            observer.update(getStateNodeList());
        }
    }
}
