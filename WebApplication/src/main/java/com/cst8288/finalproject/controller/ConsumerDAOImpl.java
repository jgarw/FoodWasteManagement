package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will be used to interact with the Retailers table in the database.
 * It will implement the UserTypeDAO interface to use the getIdFromEmail method.
 */
public class ConsumerDAOImpl implements UserTypeDAO{

    private Connection connection;

    public ConsumerDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Fetches the retailer_id using the email of the retailer.
     * @param email The email of the user who is a retailer.
     * @return The retailer_id corresponding to the given email.
     * @throws SQLException If there is any issue executing the query.
     */
    public int getIdByEmail(String email) {
        // This query assumes that the Users table and Retailers table are linked by user_id
        String query = "SELECT r.retailer_id FROM Retailers r JOIN Users u ON r.user_id = u.user_id WHERE u.email = ?";
        
        int retailerId = 0;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    retailerId = resultSet.getInt("retailer_id");
                } else {
                    throw new SQLException("Retailer not found with email: " + email);
                }
            }catch(SQLException e) {
                System.out.println("Error fetching retailer ID: " + e.getMessage());
            }
            return retailerId;
        }

}
