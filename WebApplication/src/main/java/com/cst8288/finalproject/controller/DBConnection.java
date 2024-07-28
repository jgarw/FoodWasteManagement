package com.cst8288.finalproject.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/**
 * This class will hold all methods and variables for a database connection, 
 * and will implement the singleton pattern to that only one instance can be instantiated at a time
 */
public class DBConnection {
	
	private Connection connection;
	private static DBConnection instance = null;

	/**
	 * private constructor to adhere to singleton pattern.
	 * 
	 * This constructor will provide a database connection calling the 'connecFromFile' method.
	 * This method will read the database.properties file and establish a connection to the database.
	 */
	private DBConnection(){
		connectFromFile();
	}

	/**
     * connectFromFile method to establish connection to database using values from database.properties file.
	 * This file is read using the Properties class and the values are stored in variables.
     */
    private void connectFromFile(){
        //Ensure that you use the Properties class to load values from the database.properties file
        Properties dbConnection = new Properties();
        
        //input path to read from database.properties file
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("data/database.properties")) {
            if (in == null) {
                throw new IOException("Properties file not found");
            }
            dbConnection.load(in);
            System.out.println("Properties file loaded");
        } catch(IOException e) {
            System.out.println("Error reading properties: " + e.getMessage());
        }

        //read database.properties file and store values into variables
        String dbms = dbConnection.getProperty("db");
        String dbName = dbConnection.getProperty("name");
        String host = dbConnection.getProperty("host");
        String password = dbConnection.getProperty("pass");
        String port = dbConnection.getProperty("port");
        String username = dbConnection.getProperty("user");

        //create serverUrl from database.properties values
        String serverUrl = "jdbc:"+dbms+"://"+host+":"+port+"/"+dbName;
       
        try{
            //load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connect to mysql database using serverUrl, username and password obtained from database.properties
            connection = DriverManager.getConnection(serverUrl, username, password);
            System.out.println("connection successful!");
            
        }
        //Catch exception thrown when trying to connect to database with serverUrl, username, password
        catch(SQLException e){
            System.out.println("Failed to connect to database!");
        }
        //catch exception thrown when trying to load JDBC Driver Class
        catch(ClassNotFoundException e){
            System.out.println("class not found");
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
