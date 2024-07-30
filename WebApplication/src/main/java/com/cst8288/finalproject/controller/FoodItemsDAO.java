package com.cst8288.finalproject.controller;

import java.sql.Date;
import java.util.List;

import com.cst8288.finalproject.model.FoodItem;

/**
 * This interface will define the methods used to interact with the FoodItems table.
 * These methods will only be available to users of type Retailer.
 * 
 * @see FoodItem
 */
public interface FoodItemsDAO {

    /**
     * Method for adding a food item to the FoodItems table.
     * @param name
     * @param expirationDate
     * @param price
     * @param surplus
     * @param listingType
     * @param retailerEmail
     */
    public void addFoodItem(String name, Date expirationDate, double price, boolean surplus, String listingType, String retailerEmail);

    /**
     * Method for deleting a food item from the FoodItems table.
     * @param name
     * @param expirationDate
     * @param price
     * @param surplus
     * @param listingType
     * @param retailerEmail
     */
    public void updateFoodItem(String name, Date expirationDate, double price, boolean surplus, String listingType, String retailerEmail);

    /**
     * Method for marking a food item as surplus in the FoodItems table.
     * @param id
     */
    public void markItemSurplus(int id);

    /**
     *  Method for retrieving a food item from the FoodItems table by ID.
     * @param id
     * @return
     */
    public FoodItem retrieveFoodItem(int id);

    /**
     * Method for retrieving all food items from the FoodItems table by retailer email.
     * @param retailerEmail
     * @return
     */
    public List<FoodItem> retrieveAllFoodItems(String retailerEmail);

}
