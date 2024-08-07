package com.cst8288.finalproject.controller;

import java.util.List;

public interface SubscriberDAO {

   /**
     * Method for subscribing a user to receive alerts.
     * @param email email of the user to be subscribed
     */
    public void subscribeToAlerts(String email);

    /**
     * Method to retrieve all subscribers existing in the database
     * @return List of all subscribers
     */
    public List<String> retrieveSubscribers();

}
