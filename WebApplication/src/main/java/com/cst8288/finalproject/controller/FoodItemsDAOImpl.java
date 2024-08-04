package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                String query = "INSERT INTO FoodItems (item_name, expiration_date, price, surplus, listing_type, retailer_id) VALUES (?, ?, ?, ?, ?, ?)";

                //create a RetailerDAOImpl object to get the retailer ID from the email, then enter ID into fooditem table
                RetailerDAOImpl retailerDAO = new RetailerDAOImpl();

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name);
                    statement.setDate(2, expirationDate);
                    statement.setDouble(3, price);
                    statement.setBoolean(4, surplus);
                    statement.setString(5,  listingType);
                    statement.setInt(6, retailerDAO.getIdByEmail(retailerEmail)); // Set retailer ID
                    statement.executeUpdate();
                    System.out.println("Food item added successfully.");
                    
                } catch (SQLException e) {
                    System.out.println("Error adding food item: " + e.getMessage());
                }

    }

    /**
     * Updates the quantity of a food item in the database.
     * @param itemId The ID of the food item to update.
     * @param newQuantity The new quantity to set.
     */
    public void updateItemQuantity(int itemId, int newQuantity) throws SQLException {
        String query = "UPDATE FoodItems SET quantity = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newQuantity);
            statement.setInt(2, itemId);
        } catch (SQLException e) {
            System.out.println("Error updating item quantity: " + e.getMessage());
        }
    }


    /**
     * Method for retrieving a food item from the FoodItems table by ID.
     * @param id
     * @return
     */
    @Override
    public FoodItem retrieveFoodItem(int id) {
        String query = "SELECT * FROM FoodItems WHERE item_id = ?";
        FoodItem foodItem = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("item_name");
                    Date expirationDate = rs.getDate("expiration_date");
                    double price = rs.getDouble("price");
                    boolean surplus = rs.getBoolean("surplus");
                    String listingType = rs.getString("listing_type");

                    foodItem = new FoodItem(name, expirationDate, price, surplus, listingType);
                } else {
                    System.out.println("No food item found with id: " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving food item: " + e.getMessage());
        }
        return foodItem;
    }



    /**
     * method to mark an item in the FoodItems table as surplus
     */
    @Override
    public void markItemSurplus(int id) {
        String query = "UPDATE FoodItems SET surplus = ? WHERE item_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setInt(2, id);
            statement.executeUpdate();
            System.out.println("Item marked as surplus successfully.");
        } catch (SQLException e) {
            System.out.println("Error marking item as surplus: " + e.getMessage());
        }
    }

	/**
	 * Retrieve all items from the database
	 */
	@Override
	public List<FoodItem> retrieveAllFoodItems(String retailerEmail) {
	    List<FoodItem> foodItems = new ArrayList<>();
	    String query = "SELECT * FROM FoodItems WHERE retailer_id = ?";
	
	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        
	        RetailerDAOImpl retailerDAO = new RetailerDAOImpl();
	        int retailerId = retailerDAO.getIdByEmail(retailerEmail);
	        statement.setInt(1, retailerId);
	
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                FoodItem item = new FoodItem();
	                item.setId(resultSet.getInt("item_id"));
	                item.setName(resultSet.getString("item_name"));
	                item.setExpirationDate(resultSet.getDate("expiration_date"));
	                item.setPrice(resultSet.getDouble("price"));
	                item.setSurplus(resultSet.getBoolean("surplus"));
	                item.setListingType(resultSet.getString("listing_type"));
	                item.setQuantity(resultSet.getInt("quantity"));
	                // Add the food item to the list
	                foodItems.add(item);
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Error retrieving food items: " + e.getMessage());
	    }
	    return foodItems;
	}
	
	
	
	/**
	 * retrieve all items in the database that available for donation for Organizations.
	 * @return foodItems array list of donations
	 */
	public List<FoodItem> retrieveAvailableDonations() {
	    List<FoodItem> foodItems = new ArrayList<>();
	    String query = "SELECT * FROM FoodItems where listing_type = 'donation' ";
	
	    try (PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {
	
	        while (resultSet.next()) {
	            FoodItem item = new FoodItem();
	            item.setId(resultSet.getInt("id"));
	            item.setName(resultSet.getString("item_name"));
	            item.setExpirationDate(resultSet.getDate("expiration_date"));
	            item.setQuantity(resultSet.getInt("quantity"));
	            /* need to add the retailer id or name in the fooditem object class */
	            foodItems.add(item);
	        }
	
	    } catch (SQLException e) {
	        System.out.println("Error retrieving food items: " + e.getMessage());
	    }
	    return foodItems;
	}
	
	/**
	 * Retrieve discounted items from retailers for consumers to view/purchase
	 * @return Discounted items for consumers
	 */
	public List<FoodItem> retrieveDiscountedItems() {
	    List<FoodItem> foodItems = new ArrayList<>();
	    String query = "SELECT * FROM FoodItems where listing_type = 'discount' ";
	
	    try (PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {
	
	        while (resultSet.next()) {
	            FoodItem item = new FoodItem();
	            item.setId(resultSet.getInt("id"));
	            item.setName(resultSet.getString("item_name"));
	            item.setExpirationDate(resultSet.getDate("expiration_date"));
	            item.setPrice(resultSet.getDouble("price"));
	            item.setQuantity(resultSet.getInt("quantity"));
	            /* need to add the retailer id or name in the fooditem object class */
	            foodItems.add(item);
	        }
	
	    } catch (SQLException e) {
	        System.out.println("Error retrieving food items: " + e.getMessage());
	    }
	    return foodItems;
	}

	@Override
	public void updateFoodItem(int itemId, String name, Date expirationDate, double price, boolean surplus, String listingType,
			String retailerEmail) {

		String query = "UPDATE FoodItems SET item_name = ?, expiration_date = ?, price = ?, surplus = ?, listing_type = ? WHERE item_id = ?";

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        // Set the parameters for the PreparedStatement
	        statement.setString(1, name);
	        statement.setDate(2, expirationDate);
	        statement.setDouble(3, price);
	        statement.setBoolean(4, surplus);
	        statement.setString(5, listingType);
	        statement.setInt(6, itemId);

	        // Execute the update
	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Food item updated successfully.");
	        } else {
	            System.out.println("No food item found with id: " + itemId);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error updating food item: " + e.getMessage());
	    }
	}

/**
 * Retreive the quantity of a specific item
 * @param itemId
 * @return
 */
public int getCurrentQuantity(int itemId) {
    String query = "SELECT quantity FROM FoodItems WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, itemId);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("quantity");
            }
        }
    } catch (SQLException e) {
        System.out.println("Error retrieving current quantity: " + e.getMessage());
    }
    return 0; // Or handle error appropriately
}

}