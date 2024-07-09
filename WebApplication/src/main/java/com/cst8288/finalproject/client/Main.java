package com.cst8288.finalproject.client;

import java.util.Scanner;

import com.cst8288.finalproject.database.UserDAOImpl;
import com.cst8288.finalproject.users.Consumer;
import com.cst8288.finalproject.users.Organization;
import com.cst8288.finalproject.users.Retailer;
import com.cst8288.finalproject.users.User;
import com.cst8288.finalproject.users.UserFactory;

/**
 * This class will act as the driver class that will execute the program.
 * 
 * Right now it is set up to allow a user to be registered into the database.
 */
public class Main 
{
    /**
     * main method that will drive the program.
     * @param args command line arguments
     */
    public static void main( String[] args )
    {


        String name;
        String email;
        String userType;
        String password;
        String phone;

        Scanner scan = new Scanner(System.in);
        UserDAOImpl dao = new UserDAOImpl();

        //provide user with choice of loggin in or registering as new user into database
        System.out.println("Would you like to log-in or register?");
        System.out.println("1. Log-in");
        System.out.println("2. Register");

        int choice = scan.nextInt();
        scan.nextLine(); //buffer line, dont remove

        //switch to handle logic of user selection
        switch(choice){

            //if user selects 1 (login)
            case 1:

                System.out.println("Enter email: ");
                email = scan.nextLine();

                System.out.println("enter password: ");
                password = scan.nextLine();

                //authorize user based on entered email and password
                User loggedInUser = dao.authUser(email, password);

                //check what type of user is logged in and show necessary information
                if(loggedInUser instanceof Retailer){
                    System.out.println("Welcome Retailer: " + loggedInUser.getName());

                    //testing if user retailer can add a new food item to the database
                    ((Retailer)loggedInUser).addInventory();


                }else if(loggedInUser instanceof Organization){
                    System.out.println("Welcome Organization: " + loggedInUser.getName());
                }else if(loggedInUser instanceof Consumer){
                    System.out.println("Welcome Consumer: " + loggedInUser.getName());
                }

            break;

            //if user selects 2 (register)
            case 2:

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
                
                //create a new user object with factory based on entered information 
                User newUser = UserFactory.createUser(userType, name, email, password, phone);

                //insert new user into tables
                dao.insertUser(newUser);
            
            break;

        }
        
        scan.close();
    }
}
