package com.cst8288.finalproject.controller;

import java.util.List;

public interface SubscriberDAO {

   /**
     * Method for subscribing a user to receive alerts.
     * @param email email of the user to be subscribed
     */
    public void subscribeToAlerts(String email);

    public List<String> retrieveSubscribers();

}
