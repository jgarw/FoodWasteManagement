package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cst8288.finalproject.model.Purchase;

/**
 * This class inserts the purchased items purchased by the consumer into the corresponding table inside the database
 */
public class PurchasedItemsDAOImpl implements PurchasedItemsDAO {

    private Connection connection;

    public PurchasedItemsDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Method for inserting purchases into the database
     */
    @Override
    public void addPurchase(int itemId, String consumerEmail, int quantity) {
        String query = "INSERT INTO purchases (item_id, consumer_id, quantity) VALUES (?, ?, ?)";

        ConsumerDAOImpl consumerDAO = new ConsumerDAOImpl();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, itemId);
            statement.setInt(2, consumerDAO.getIdByEmail(consumerEmail));
            statement.setInt(3, quantity);
            statement.executeUpdate();
            System.out.println("Food purchase added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding food purchase: " + e.getMessage());
        }
    }

    /**
     * Method to retrieve all purchases for a given consumer's email
     */
    public List<Purchase> retrieveAllPurchases(String consumerEmail) {
        List<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM purchases WHERE consumer_id = ?";

        ConsumerDAOImpl consumerDAO = new ConsumerDAOImpl();
        int consumerId = consumerDAO.getIdByEmail(consumerEmail);

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, consumerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setPurchase_id(resultSet.getInt("purchase_id"));
                purchase.setItem_id(resultSet.getInt("item_id"));
                purchase.setConsumer_id(resultSet.getInt("consumer_id"));
                purchase.setQuantity(resultSet.getInt("quantity"));
                purchases.add(purchase);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving purchases: " + e.getMessage());
        }
        return purchases;
    }
}
