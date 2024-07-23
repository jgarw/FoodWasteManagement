package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.cst8288.finalproject.model.FoodItem;

public class FoodItemsDAOImpl implements FoodItemsDAO{

    private Connection connection;

    /**
     * Constructor for FoodItemsDAOImpl class
     * This constructor will get the connection to the database
     */
    public FoodItemsDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Method for adding a food item to the FoodItems table.
     * 
     * 
     * 
     * THE WAY ITS SET UP RIGHT NOW: 
     * WHEN A RETAILER CALLS THIS TO ADD A FOOD ITEM TO THEIR INVENTORY, 
     * IT AUTOMATICALLY PLUGS IN THEIR EMAIL THROUGH THE METHOD HEADER (USE RETAILER.GETEMAIL()),
     * THEN USES THE GETRETAILERIDBYEMAIL METHOD FROM RETAILERDAOIMPL TO GET THE RETAILER ID AND INSERT THAT INTO FOODITEMS TABLE
     */
    @Override
    public void addFoodItem(String name, Date expirationDate, double price, boolean surplus, String listingType,
            String retailerEmail) {
                String query = "INSERT INTO FoodItems (item_name, expiration_date, price, surplus, retailer_id) VALUES (?, ?, ?, ?, ?)";

                //create a RetailerDAOImpl object to get the retailer ID from the email, then enter ID into fooditem table
                RetailerDAOImpl retailerDAO = new RetailerDAOImpl();

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name);
                    statement.setDate(2, expirationDate);
                    statement.setDouble(3, price);
                    statement.setBoolean(4, surplus);
                    statement.setInt(5, retailerDAO.getIdByEmail(retailerEmail)); // Set retailer ID
                    statement.executeUpdate();
                    System.out.println("Food item added successfully.");
                    
                } catch (SQLException e) {
                    System.out.println("Error adding food item: " + e.getMessage());
                }

    }

    @Override
    public FoodItem retriveAllFoodItems(String retailerEmail) {
        
        return null;

    }

    

}
