package com.cst8288.finalproject.model;

import java.sql.Date;
import java.util.Scanner;

import com.cst8288.finalproject.controller.FoodItemsDAO;
import com.cst8288.finalproject.controller.FoodItemsDAOImpl;
import com.cst8288.finalproject.controller.RetailerSubject;

/**
 * This class extends the User class and provides a constructor for creating a Retailer object.
 * @see User
 */
public class Retailer extends User{

	/**
	 * create a reference to RetailerSubject using aggregation.
	 * This retailer subject will be used to notify subscribers of updates
	 */
    private RetailerSubject retailerSubject;

    /**
     * Constructor for Retailer class
     * @param name name of the retailer
     * @param email email of the retailer
     * @param password password of the retailer
     * @param phone phone of the retailer
     * @param userType type of the retailer
     */
    public Retailer(String name, String email, String password, String phone, String userType) {
       super(name, email, password, phone, userType);
       //use the singleton pattern to get the instance of the RetailerSubject
       this.retailerSubject = RetailerSubject.getInstance();

    }


    /**
     * Method for updating a food item in the retailers inventory
     *
     * When marked as surplus, a notification is sent to all observers in Subscribers table by using the notifyObservers method from RetailerSubject
     *
     * @see RetailerSubject for notifyObservers method logic
     *
     * @param item_id
     */
    public void markItemSurplus(int item_id){
        FoodItemsDAO inventory = new FoodItemsDAOImpl();
        inventory.markItemSurplus(item_id);

        FoodItem item = inventory.retrieveFoodItem(item_id);

        // Notify all observers of the change and mention the
        retailerSubject.notifyObservers("New hot food in your area! " + this.getName() + " just marked food item" +  item.name + " as surplus!");
    }

}
