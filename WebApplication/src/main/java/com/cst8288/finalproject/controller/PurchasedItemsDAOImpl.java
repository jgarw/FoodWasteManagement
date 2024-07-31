package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cst8288.finalproject.model.FoodItem;

public class PurchasedItemsDAOImpl implements PurchasedItemsDAO {

    private Connection connection;

    public PurchasedItemsDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public void addPurchase(int item_id, String consumerEmail, int quantity) {
                String query = "INSERT INTO purchases (item_id, consumer_id, quantity) VALUES (?, ?, ?)";

                ConsumerDAOImpl consumerDAO = new ConsumerDAOImpl();

                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, item_id);
                    statement.setInt(2, consumerDAO.getIdByEmail(consumerEmail));
                    statement.setInt(3, quantity);
                    statement.executeUpdate();
                    System.out.println("Food purchase added successfully.");
                    
                } catch (SQLException e) {
                    System.out.println("Error adding food purchase: " + e.getMessage());
                }

    }
}