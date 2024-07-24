package com.cst8288.finalproject.controller;


/**
 * This interface will define the methods used to interact with the Retailer table and Retailer related tables.
 */
public interface UserTypeDAO {

    /**
     * Method for retrieving the id of a retailer from the database using email.
     * @param email email of the retailer to be retrieved
     * @return id of the retailer retrieved from the database
     */
    public int getIdByEmail(String email);

}
