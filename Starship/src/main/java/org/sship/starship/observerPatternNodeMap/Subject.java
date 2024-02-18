package org.sship.starship.observerPatternNodeMap;

/**
 * The Subject interface defines the contract for objects that can be observed by observers.
 */
public interface Subject {
    /**
     * Registers an observer to receive updates from the subject.
     *
     * @param observer The observer to register
     */
    public void registerObserver(ObserverHashMap observer);

    /**
     * Unregisters an observer from receiving updates from the subject.
     *
     * @param observer The observer to unregister
     */
    public void unregisterObserver(ObserverHashMap observer);

    /**
     * Notifies all registered observers when there is a change in the subject's state.
     */
    public void notifyObserver();
}
