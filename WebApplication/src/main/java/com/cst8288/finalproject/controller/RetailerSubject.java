package com.cst8288.finalproject.controller;

import java.util.List;

/**
 * This class will act as a concrete subject that will be observed by the UserObserver class.
 * This class will be a singleton class to ensure that there is only one instance of the RetailerSubject observers list in the application.
 */
public class RetailerSubject implements Subject {

    /**
     * Static variable to hold the instance of the RetailerSubject object.
     */
    private static RetailerSubject instance;


    /**
     * Constructor to create a RetailerSubject object with an empty list of observers.
     */
    public RetailerSubject(){
    }

    /**
     * Method to get the instance of the RetailerSubject object.
     * If the instance is null, a new instance will be created.
     * @return instance of the RetailerSubject object
     */
    public static RetailerSubject getInstance(){
        if(instance ==null){
            instance = new RetailerSubject();
        }
        return instance;
    }

    /**
     * Method to notify all observers of a change in the subject.
     * This is done by iterating through the list of subscribers and calling the sendEmail method on each observer in subscribers table
     *
     * @see EmailSender for sendEmail logic
     *
     * @param message
     */
    @Override
    public void notifyObservers(String message) {
        SubscriberDAOImpl subscriberDAO = new SubscriberDAOImpl();
        EmailSender sender = new EmailSender();

        //use the retrieveSubscribers method from SubscriberDAO to get a list of all email subscribers
        List<String> subscribers = subscriberDAO.retrieveSubscribers();

        //for each subscriber, send an email notification about the update
        for (String email : subscribers) {
            //sendNotification(email, message);
            sender.sendEmail(email, message);
        }
    }

    /**
     * Method to send a notification to a user.
     * @param email the email of the user to send the notification to
     * @param message the message to send to the user
     */
    public void sendNotification(String email, String message){
        //implement logic here to send notification to user
        System.out.println("Sending notification to: " + email + " with message: " + message);
    }



}
