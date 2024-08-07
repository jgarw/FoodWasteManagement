package com.cst8288.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will act as a concrete observer that will receive notifications about FoodAlerts if subscribed.
 */
public class UserObserver implements Observer{
    /**
     * String variable to hold the email of the user that notifications will be sent to.
     */
    private String email;

    /**
     * List of Strings to hold the notifications that the user will receive.
     */
    private List<String> notifications;


    /**
     * Constructor to create a UserObserver object with the email of the user.
     * @param email
     */
    public UserObserver(String email){
        this.email = email;
        this.notifications = new ArrayList<>();
    }

    /**
     * Override Observer interfaces update method to provide implementation for receiving notifications about Food Alerts
     * @param message
     */
    @Override
    public void update(String message) {
        notifications.add(message);
    }

    /**
     * Method to get the email of the user.
     * @return
     */
    public String getEmail(){
        return email;
    }

    /**
     * Method to return all notifications that the user will receive
     * @return Notifications
     */
    public List<String> getNotifications(){
        return notifications;
    }


}
