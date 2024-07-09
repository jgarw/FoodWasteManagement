package com.cst8288.finalproject.users;

/**
 * This class extends the User class and provides a constructor for creating an Organization object.
 * @see User
 */
public class Organization extends User{

    /**
     * Constructor for Organization class
     * @param name name of the organization
     * @param email email of the organization
     * @param password password of the organization
     * @param phone phone of the organization
     * @param userType type of the organization
     */
    public Organization(String name, String email, String password, String phone, String userType) {
        super(name, email, password, phone, userType);
    }

}
