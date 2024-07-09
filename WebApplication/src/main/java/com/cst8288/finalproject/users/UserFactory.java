package com.cst8288.finalproject.users;

/**
 * factory class that will create a new user based on user type provided.
 */
public class UserFactory {

    /**
     * method that will create User object that corresponds with user type entered
     * @param userType type of user
     * @param name name of user
     * @param email email of user
     * @param password password of user
     * @param phone phone of user
     * @return User object
     */
    public static User createUser(String userType, String name, String email, String password, String phone) {

        // switch statement to determine which type of user to create
        switch (userType.toLowerCase()) {
            case "organization":
                return new Organization(name, email, password, phone, userType);

            case "consumer":
                return new Consumer(name, email, password, phone, userType);

            case "retailer":
                return new Retailer(name, email, password, phone, userType);

            default:
                return null;
        }
    }
}
