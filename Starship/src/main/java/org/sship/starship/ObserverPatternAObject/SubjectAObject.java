package org.sship.starship.ObserverPatternAObject;

/**
 * The SubjectAObject interface represents the subject in the observer pattern
 * for observing AObject instances.
 */
public interface SubjectAObject {
    /**
     * Registers an observer to receive updates.
     *
     * @param observer The observer to register
     */
    void registerObserver(ObserverAObject observer);

    /**
     * Unregisters an observer.
     *
     * @param observer The observer to unregister
     */
    void unregisterObserver(ObserverAObject observer);

    /**
     * Notifies all registered observers.
     */
    void notifyObserver();
}
