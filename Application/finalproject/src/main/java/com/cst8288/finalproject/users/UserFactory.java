package com.cst8288.finalproject.users;

/**
 * factory class that will create a new user based on user type provided.
 */
public class UserFactory {

    /**
     * method that will create User object that corresponds with user type entered
     * @param userType
     * @param name
     * @param email
     * @param password
     * @param phone
     * @return
     */
    public static User createUser(String userType, String name, String email, String password, String phone) {


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
