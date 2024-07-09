package com.cst8288.finalproject.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * This class will hold all methods and variables for a database connection, 
 * and will implement the singleton pattern to that only one instance can be instantiated at a time
 */
public class DBConnection {
	
	private static Connection connection = null;
	private static DBConnection instance = null;
	
	private String serverUrl = "jdbc:mysql://localhost:3306/FoodWasteReduction";
	private String userString = "root";
	private String passwordString = "password";
	private String driverString = "com.mysql.cj.jdbc.Driver";

	/**
	 * private constructor to adhere to singleton pattern.
	 * 
	 * This constructor will provide a database connection by loading the class with 'driverString' name.
	 * A connection will then be made with the server url,username and password provided to the getConnection method
	 */
	private DBConnection(){
		try{
		Class.forName(driverString);

		connection = DriverManager.getConnection(serverUrl, userString, passwordString);
		System.out.println("Connecting to the database");

		// Print connection status
		if (connection != null) {
			System.out.println("Database connection established successfully!");
		} else {
			System.out.println("Failed to establish database connection.");
		}

		} 
		/**
		 * handle exception if driver class not found
		 */
		catch(ClassNotFoundException e){
			e.getMessage();
		}
		/**
		 * handle exception if database error is thrown
		 */
		catch(SQLException e){
			e.getMessage();
		}
	}

	/**
	 * create a getInstance method to implement singleton pattern
	 * @return an instance of DBConnection
	 */
	public static DBConnection getInstance(){
		if (instance == null){
			instance = new DBConnection();
		}

		return instance;
	}

	/**
	 * provide a method to get the connection after instantiating DBConnection instance
	 * @return connection 
	 */
	public Connection getConnection(){
		return connection;
	}
	
}
