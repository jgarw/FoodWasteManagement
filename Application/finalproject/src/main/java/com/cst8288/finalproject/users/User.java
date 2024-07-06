package com.cst8288.finalproject.users;
/**
 * abstract class to hold all information regarding a User.
 * This abstract class will act as a superclass for all User sublasses (Consumer, Organization, Retailer)
 */
public class User {

    /**
     * name of user
     */
    private String name;
    /**
     * email of user
     */
    private String email;
    /**
     * password of user
     */
    private String password;
    /**
     * phone number of user
     */
    private String phone;
    /**
     * type of user
     */
    private String userType;

    /**
     * constructor for User superclass
     * @param name name of user
     * @param email email of user
     * @param password password set by user
     * @param phone phone numberof user
     * @param userType the type of user
     */
    public User(String name, String email, String password, String phone, String userType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.userType = userType;
    }

    /**
     * Getter for name
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for email
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for password
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for phone
     * @return The phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter for phone
     * @param phone The phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter for userType
     * @return The userType of the user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Setter for userType
     * @param userType The userType to set
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
