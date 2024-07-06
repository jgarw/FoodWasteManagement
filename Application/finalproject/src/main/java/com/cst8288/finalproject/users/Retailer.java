package com.cst8288.finalproject.users;

import java.sql.Date;
import java.util.Scanner;

import com.cst8288.finalproject.database.FoodItemsDAOImpl;

/**
 * This class extends the User class and provides a constructor for creating a Retailer object.
 * @see User
 */
public class Retailer extends User{

    /**
     * Constructor for Retailer class
     * @param name name of the retailer
     * @param email email of the retailer
     * @param password password of the retailer
     * @param phone phone of the retailer
     * @param userType type of the retailer
     */
    public Retailer(String name, String email, String password, String phone, String userType) {
       super(name, email, password, phone, userType);
    }
    
    /**
     * Method for adding a food item to the retailers inventory
     */
    public void addInventory() {
                
                // Create a new FoodItemsDAOImpl object to interact with the FoodItems table
                FoodItemsDAOImpl inventory = new FoodItemsDAOImpl();

                Scanner scanner = new Scanner(System.in);

                // Prompt the user to enter the name of the food item
                System.out.println("Enter the name of the food item: ");

                String foodItemName = scanner.nextLine();

                // Prompt the user to enter the expiration date of the food item
                System.out.println("Enter the expiration date of the food item (yyyy-mm-dd): ");
                Date itemExpirationDate = Date.valueOf(scanner.nextLine());

                // Prompt the user to enter the price of the food item
                System.out.println("Enter the price of the food item: ");
                double itemPrice = scanner.nextDouble();

                // Prompt the user to enter the surplus status of the food item
                System.out.println("Is the food item surplus? (true/false): ");
                boolean isItemSurplus = scanner.nextBoolean();
                scanner.nextLine(); //buffer line

                // Prompt the user to enter the listing type of the food item
                System.out.println("Enter the listing type of the food item: ");
                String itemListingType = scanner.nextLine();


                // Call the addFoodItem method to add the food item to the FoodItems table
                inventory.addFoodItem(foodItemName, itemExpirationDate, itemPrice, isItemSurplus, itemListingType, getEmail());

                scanner.close();

    }

}
