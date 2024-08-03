package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class will be used to interact with the consumers table in the database.
 * It will implement the UserTypeDAO interface to use the getIdFromEmail method.
 */
public class ConsumerDAOImpl implements UserTypeDAO{

    private Connection connection;

    public ConsumerDAOImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    /**
     * Fetches the consumer_id using the email of the consumer.
     * @param email The email of the user who is a consumer.
     * @return The consumer_id corresponding to the given email.
     * @throws SQLException If there is any issue executing the query.
     */
    public int getIdByEmail(String email) {
        // This query assumes that the Users table and consumers table are linked by user_id
        String query = "SELECT c.consumer_id FROM consumers c JOIN Users u ON c.user_id = c.user_id WHERE c.email = ?";
        
        int consumerId = 0;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    consumerId = resultSet.getInt("consumer_id");
                } else {
                    throw new SQLException("consumer not found with email: " + email);
                }
            }catch(SQLException e) {
                System.out.println("Error fetching consumer ID: " + e.getMessage());
            }
            return consumerId;
        }

}
