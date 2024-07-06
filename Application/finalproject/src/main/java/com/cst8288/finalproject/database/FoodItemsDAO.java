package com.cst8288.finalproject.database;

import java.sql.Date;

import com.cst8288.finalproject.items.FoodItem;

/**
 * This interface will define the methods used to interact with the FoodItems table.
 * These methods will only be available to users of type Retailer.
 * 
 * @see FoodItem
 */
public interface FoodItemsDAO {

    public void addFoodItem(String name, Date expirationDate, double price, boolean surplus, String listingType, String retailerEmail);
    public FoodItem retriveAllFoodItems(String retailerEmail);

}
