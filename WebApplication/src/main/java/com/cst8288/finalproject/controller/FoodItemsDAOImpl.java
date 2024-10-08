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
	 * When a retailer calls this method to add a food item to their inventory,
	 * it automatically plugs in their email through the method header (use retailer.getEmail()),
	 * then uses the getRetailerIdByEmail method from RetailerDAOImpl to get the retailer ID and insert that into the FoodItems table.
	 */
    @Override
    public void addFoodItem(String name, Date expirationDate, int quantity, double price, boolean surplus, String listingType,
            String retailerEmail) {
                String query = "INSERT INTO FoodItems (item_name, expiration_date, quantity, price, surplus, listing_type, retailer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

                //create a RetailerDAOImpl object to get the retailer ID from the email, then enter ID into fooditem table
                RetailerDAOImpl retailerDAO = new RetailerDAOImpl();

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, name);
                    statement.setDate(2, expirationDate);
                    statement.setInt(3, quantity);
                    statement.setDouble(4, price);
                    statement.setBoolean(5, surplus);
                    statement.setString(6,  listingType);
                    statement.setInt(7, retailerDAO.getIdByEmail(retailerEmail)); // Set retailer ID
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
    public void updateItemQuantity(int itemId, int newQuantity) {
        String query = "UPDATE FoodItems SET quantity = ? WHERE item_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newQuantity);
            statement.setInt(2, itemId);
            statement.executeUpdate();
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
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    boolean surplus = rs.getBoolean("surplus");
                    String listingType = rs.getString("listing_type");

                    foodItem = new FoodItem(name, expirationDate, quantity, price, surplus, listingType);
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
	    String query = "SELECT * FROM FoodItems where quantity > 0 AND listing_type = 'donation' ";

	    try (PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            FoodItem item = new FoodItem();
	            item.setId(resultSet.getInt("item_id"));
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
	    String query = "SELECT * FROM FoodItems where quantity > 0 AND listing_type = 'discounted'";

	    try (PreparedStatement statement = connection.prepareStatement(query);
	         ResultSet resultSet = statement.executeQuery()) {

	        while (resultSet.next()) {
	            FoodItem item = new FoodItem();
	            item.setId(resultSet.getInt("item_id"));
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

	/**
	 * Update all parameters of an item based on itemId
	 * @param itemId the ID of the food item to update
	 * @param name the new name of the food item
	 * @param expirationDate the new expiration date of the food item
	 * @param quantity the new quantity of the food item
	 * @param price the new price of the food item
	 * @param surplus whether the food item is surplus or not
	 * @param listingType the new listing type of the food item
	 * @param retailerEmail the email of the retailer updating the food item
	 */
	@Override
	public void updateFoodItem(int itemId, String name, Date expirationDate, int quantity, double price, boolean surplus, String listingType,
			String retailerEmail) {

		String query = "UPDATE FoodItems SET item_name = ?, expiration_date = ?, quantity = ?, price = ?, surplus = ?, listing_type = ? WHERE item_id = ?";

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        // Set the parameters for the PreparedStatement
	        statement.setString(1, name);
	        statement.setDate(2, expirationDate);
	        statement.setInt(3, quantity);
	        statement.setDouble(4, price);
	        statement.setBoolean(5, surplus);
	        statement.setString(6, listingType);
	        statement.setInt(7, itemId);

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
	 * Delete an item based on itemId
	 * @param itemId
	 */
	@Override
	public void deleteFoodItem(int itemId) {
	    String query = "DELETE FROM FoodItems WHERE item_id = ?";

	    try (PreparedStatement statement = connection.prepareStatement(query)) {
	        // Set the parameter for the PreparedStatement
	        statement.setInt(1, itemId);

	        // Execute the update
	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Food item deleted successfully.");
	        } else {
	            System.out.println("No food item found with id: " + itemId);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error deleting food item: " + e.getMessage());
	    }
	}


	/**
	 * Retrieve the quantity of a specific item
	 * @param itemId the ID of the food item to update
	 * @return The item's quantity
	 */
	public int getCurrentQuantity(int itemId) {
	    String query = "SELECT quantity FROM FoodItems WHERE item_id = ?";
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