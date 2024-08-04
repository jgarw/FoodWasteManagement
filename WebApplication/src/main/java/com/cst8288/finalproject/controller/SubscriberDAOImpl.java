package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDAOImpl implements SubscriberDAO{

    private Connection connection;

    /**
     * Constructor for SubscriberDAOImpl class
     * This constructor establishes a connection to the database when called.
     *
     * @return connection to the database from DBConnection class
     */
    public SubscriberDAOImpl(){
        //establish a database connection when constructor is called
        this.connection = DBConnection.getInstance().getConnection();
    }

     /**
     * method for subscribing user to alerts.
     * @param email email of user to be subscribed
     */
    @Override
    public void subscribeToAlerts(String email) {
        //create a query for inserting the table
        String query = "INSERT INTO Subscribers (email) VALUES (?)";

		try{
			PreparedStatement statement = connection.prepareStatement(query);

			//insert users email into email column
			statement.setString(1, email);


			// execute the SQL statemetn
			statement.executeUpdate();

			System.out.println("Successfully subscribed to food alerts!");
        }
        catch(SQLException e){
            System.out.println("Error subscribing user. " + e.getMessage());
        }
    }

    /**
     * Method for retrieving all subscriber emails.
     * @return List of subscriber emails
     */
    @Override
    public List<String> retrieveSubscribers() {
        String query = "SELECT email FROM Subscribers";
        List<String> emails = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving subscribers. " + e.getMessage());
        }

        return emails;
    }


}
