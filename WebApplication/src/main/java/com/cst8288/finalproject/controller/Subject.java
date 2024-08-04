package com.cst8288.finalproject.controller;

/**
 * This interface will act as a Subject that will be observed by the UserObserver class.
 */
public interface Subject {

    /**
     * Method to notify all observers of a change in the subject.
     * @param message
     */
    public void notifyObservers(String message);


}
