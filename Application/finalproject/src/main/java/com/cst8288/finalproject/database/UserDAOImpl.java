package com.cst8288.finalproject.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cst8288.finalproject.users.*;

public class UserDAOImpl implements UserDAO{

    private Connection connection;

    public UserDAOImpl(){
        //establish a database connection when constructor is called
        this.connection = DBConnection.getInstance().getConnection();
    }
    
    /**
     * Method for creating a new user and inserting into Users table and underlying related tables (CONSUMERS, RETAILERS, ORGANIZATION)
     */
    @Override
    public void createUser(User user) {
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
			e.getMessage();
		}
    }

    /**
     * method for retrieving/selecting user based on email
     */
    @Override
    public User retrieveUser(String email) {
        String query = "SELECT * FROM USERS WHERE email = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                String name = result.getString("name");
                String password = result.getString("password");
                String userType = result.getString("user_type");
                String phone = result.getString("phone"); 

                User selectedUser = UserFactory.createUser(userType, name, email, password, phone);
                return selectedUser;
            }
        }catch(SQLException e){
            System.out.println("temp SQL error handling");
        }

        return null;
    }

    @Override
    public void updateUser(String email) {
        
    }

    @Override
    public void deleteUser(String email) {
        
    }

    /**
     * method for logging in a user based on entered email and password
     */
    @Override
    public boolean authUser(String email, String password){

        User user = retrieveUser(email);
        if (user == null){
            System.out.println("User not found!");
            return false;
        }
        if(user.getPassword().equals(password)){
            System.out.println("Log-in successful!");
            return true;
        }else{
            System.out.println("An error occured. Please check email and password.");
            return false;
        }

        

    }

}
