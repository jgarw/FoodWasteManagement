package com.cst8288.finalproject.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cst8288.finalproject.model.*;

/**
 * This class implements the UserDAO interface and provides methods for creating, retrieving, updating, deleting and authenticating users in the database.
 * 
 * @see UserDAO
 */
public class UserDAOImpl implements UserDAO{

    private Connection connection;

    /**
     * Constructor for UserDAOImpl class
     * This constructor establishes a connection to the database when called.
     * 
     * @return connection to the database from DBConnection class
     */
    public UserDAOImpl(){
        //establish a database connection when constructor is called
        this.connection = DBConnection.getInstance().getConnection();
    }
    
    /**
     * Method for creating a new user and inserting into Users table and underlying related tables (CONSUMERS, RETAILERS, ORGANIZATION).
     * @param user user object to be created
     */
    @Override
    public void insertUser(User user) {
    //create a query for inserting into the table. the '?'s are placeholders for values that will be set with get methods from Event
		String query = "INSERT INTO USERS (name, email, password, user_type, phone) VALUES (?, ?, ?, ?, ?)";

		try{
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			//insert users name into name column
			statement.setString(1, user.getName());

			//insert users email into email column
			statement.setString(2, user.getEmail());

            //insert users password into password column
			statement.setString(3, user.getPassword());

			//insert users type into suser_type column
			statement.setString(4, user.getUserType());

            //insert users phone into phone column
            statement.setString(5, user.getPhone());
            

			// execute the SQL statemetn
			statement.executeUpdate();

             // Retrieve the generated user_id
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
            int userId = generatedKeys.getInt(1);
            String subQuery = null;

                //If the user created is a consumer, insert the generated user_id into the consumer table
                if(user instanceof Consumer){
                    subQuery = "INSERT INTO CONSUMERS (user_id) VALUES (?)";
                }
                //If the user created is a retailer, insert the generated user_id into the retailer table
                else if(user instanceof Retailer){
                    subQuery = "INSERT INTO RETAILERS (user_id) VALUES (?)";
                }
                //If the user created is an organization, insert the generated user_id into the organization table
                else if(user instanceof Organization){
                    subQuery = "INSERT INTO ORGANIZATIONS (user_id) VALUES (?)";
                }

                PreparedStatement subStatement = connection.prepareStatement(subQuery);
                subStatement.setInt(1, userId);
                subStatement.executeUpdate();

		    }
        }
		// handle possible exception thrown when trying to insert into table
		catch(SQLException e){
			System.out.println("Error inserting user. " + e.getMessage());
		}
    }

    /**
     * method for retrieving/selecting user based on email.
     * @param email email of user to be retrieved
     * @return selected user
     */
    @Override
    public User retrieveUser(String email) {
        //create a query for selecting from the table
        String query = "SELECT * FROM USERS WHERE email = ?";
        //create a user object to store the selected user
        User selectedUser = null;

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            //execute the query and store the result in a ResultSet object
            ResultSet result = statement.executeQuery();

            if(result.next()){
                String name = result.getString("name");
                String password = result.getString("password");
                String userType = result.getString("user_type");
                String phone = result.getString("phone"); 

                //create a user object based on the fields retrieved from the database
                selectedUser = UserFactory.createUser(userType, name, email, password, phone);
                //return the selected user
                return selectedUser;
            }else{
                //if no user is found, print a message
                System.out.println("User not found!");
            }
        }catch(SQLException e){
            System.out.println("Error retrieving user." + e.getMessage());
        }

        return selectedUser;
    }

    /**
     * method for updating user based on email.
     * @param email email of user to be updated
     * @param name new name of user
     * @param password new password of user
     * @param phone new phone of user
     */
    @Override
    public void updateUser(String email, String name, String password, String phone) {
        
        //create a query for updating the table
        String query = "UPDATE USERS SET name = ?, password = ?, phone = ? WHERE email = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error updating user." + e.getMessage());
        }

    }

    /**
     * method for deleting user based on email.
     * @param email email of user to be deleted
     */
    @Override
    public void deleteUser(String email) {

        //create a query for deleting from the table
        String query = "DELETE FROM USERS WHERE email = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error deleting user." + e.getMessage());
        }
        
    }

    /**
     * method for logging in a user based on entered email and password.
     * @param email email of user to be logged in
     * @param password password of user to be logged in
     */
    @Override
    public User authUser(String email, String password){

        User user = retrieveUser(email);
        if (user == null){
            System.out.println("User not found!");
            return null;
        }
        if(user.getPassword().equals(password)){
            System.out.println("Log-in successful!");
            return user;
        }else{
            System.out.println("An error occured. Please check email and password.");
            return null;
        }

        

    }

}
