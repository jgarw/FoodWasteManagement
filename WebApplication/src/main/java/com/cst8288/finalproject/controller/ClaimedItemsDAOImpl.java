package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class inserts items that were claimed by the organization into the database
 */
public class ClaimedItemsDAOImpl implements ClaimedItemsDAO{

    /*
     * establish a connection
     */
    private Connection connection;

    /**
     * instantiate an instance for connection
     */
    public ClaimedItemsDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Method for adding items that were purchased into the database.
     */
    @Override
    public void addClaim(int item_id, String consumerEmail, int quantity) {
                String query = "INSERT INTO claims (item_id, consumer_id, quantity) VALUES (?, ?, ?)";

                OrganizationDAOImpl organizationDAO = new OrganizationDAOImpl();

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, item_id);
                    statement.setInt(2, organizationDAO.getIdByEmail(consumerEmail));
                    statement.setInt(3, quantity);
                    statement.executeUpdate();
                    System.out.println("Food purchase added successfully.");
                    
                } catch (SQLException e) {
                    System.out.println("Error adding food purchase: " + e.getMessage());
                }

    }
    
}
