package com.cst8288.finalproject.users;

/**
 * This class extends the User class and provides a constructor for creating a Consumer object.
 */
public class Consumer extends User {

    /**
     * Constructor for Consumer class
     * @param name name of the consumer
     * @param email email of the consumer
     * @param password password of the consumer
     * @param phone phone of the consumer
     * @param userType type of the consumer
     */
    public Consumer(String name, String email, String password, String phone, String userType) {
        super(name, email, password, phone, userType);
    }

}
