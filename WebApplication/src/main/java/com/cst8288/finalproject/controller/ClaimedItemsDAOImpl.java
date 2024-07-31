package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClaimedItemsDAOImpl implements ClaimedItemsDAO{

    private Connection connection;

    public ClaimedItemsDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

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
