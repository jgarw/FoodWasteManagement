package com.cst8288.finalproject.controller;

import com.cst8288.finalproject.model.User;

/**
 * This interface provides methods for creating, retrieving, updating, deleting and authenticating users in the database.
 * It will be implemented by UserDAOImpl class.
 * 
 * @see UserDAOImpl
 */
public interface UserDAO {

    /**
     * Method for creating a new user and inserting into Users table and underlying related tables (CONSUMERS, RETAILERS, ORGANIZATION).
     * @param user user object to be created
     */
    public void insertUser(User user);

    /**
     * Method for retrieving a user from the database using email.
     * @param email email of the user to be retrieved
     * @return user object retrieved from the database
     */
    public User retrieveUser(String email);

    /**
     * Method for updating a user in the database.
     * @param email email of the user to be updated
     * @param name name of the user to be updated
     * @param password password of the user to be updated
     * @param phone phone of the user to be updated
     */
    public void updateUser(String email, String name, String password, String phone);

    /**
     * Method for deleting a user from the database.
     * @param email email of the user to be deleted
     */
    public void deleteUser(String email);

    /**
     * Method for authenticating a user in the database.
     * @param email email of the user to be authenticated
     * @param password password of the user to be authenticated
     * @return the authorized user
     */
    public User authUser(String email, String password);


}
