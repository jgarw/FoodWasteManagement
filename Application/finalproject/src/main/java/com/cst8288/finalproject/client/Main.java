package com.cst8288.finalproject.client;

import java.sql.Connection;
import java.util.Scanner;

import com.cst8288.finalproject.database.DBConnection;
import com.cst8288.finalproject.database.UserDAOImpl;
import com.cst8288.finalproject.users.User;
import com.cst8288.finalproject.users.UserFactory;

// This is a test comment for github- Hieu

/**
 * This class will act as the driver class that will execute the program.
 * 
 * Right now it is set up to allow a user to be registered into the database.
 */
public class Main 
{
    /**
     * main method that will drive the program.
     * @param args
     */
    public static void main( String[] args )
    {

        String name;
        String email;
        String userType;
        String password;
        String phone;

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter name: ");
        name = scan.nextLine();

        System.out.println("Enter email: ");
        email = scan.nextLine();

        System.out.println("enter password: ");
        password = scan.nextLine();

        System.out.println("Enter phone: ");
        phone = scan.nextLine();

        System.out.println("enter userType: ");
        userType = scan.nextLine();

        User newUser = UserFactory.createUser(userType, name, email, password, phone);

        UserDAOImpl dao = new UserDAOImpl();
        dao.createUser(newUser);

        scan.close();
        
    }
}
