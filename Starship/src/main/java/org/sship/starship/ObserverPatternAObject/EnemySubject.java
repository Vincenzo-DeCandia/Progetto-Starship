package org.sship.starship.ObserverPatternAObject;

import org.sship.starship.player.AObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The EnemySubject class represents a subject in the observer pattern
 * that maintains a list of observers interested in changes to the list of enemy objects.
 */
public class EnemySubject implements SubjectAObject {
    private final ArrayList<ObserverAObject> observerList;
    private int totalObs = 0;
    private List<AObject> enemyListState;

    /**
     * Constructs an EnemySubject object.
     */
    public EnemySubject() {
        observerList = new ArrayList<>();
    }

    /**
     * Sets the state of the enemy list and notifies observers.
     *
     * @param enemyList The list of enemy objects
     */
    public void setStateEnemyList(List<AObject> enemyList) {
        enemyListState = enemyList;
        notifyObserver();
    }

    /**
     * Gets the current state of the enemy list.
     *
     * @return The list of enemy objects
     */
    public List<AObject> getEnemyListState() {
        return enemyListState;
    }

    /**
     * Registers an observer to receive updates.
     *
     * @param observer The observer to register
     */
    @Override
    public void registerObserver(ObserverAObject observer) {
        observerList.add(observer);
        totalObs++;
    }

    /**
     * Unregisters an observer from receiving updates.
     *
     * @param observer The observer to unregister
     */
    @Override
    public void unregisterObserver(ObserverAObject observer) {
        observerList.remove(observer);
    }

    /**
     * Notifies all registered observers about changes in the enemy list state.
     */
    @Override
    public void notifyObserver() {
        for (ObserverAObject observer : observerList) {
            observer.update(getEnemyListState());
        }
    }
}
