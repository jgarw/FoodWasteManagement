package com.cst8288.finalproject.model;

import java.sql.Date;

/**
 * This class will be used to create Item objects.
 */
public class FoodItem {

    /**
     * id of the food Item
     */
    int id;

    /**
     * name of the food item
     */
    String name;
    /**
     * expiration date of the food item
     */
    Date expirationDate;
    /**
     * price of the food item
     */
    double price;
    /**
     * boolean value for surplus food item (true = surplus)
     */
    boolean surplus;
    /**
     * listing type of the food item
     */
    String listingType;

    /**
     * quantity of items being listed
     */
    public int quantity;

    /**
     * Constructor for FoodItem class
     * @param name name of the food item
     * @param expirationDate expiration date of the food item
     * @param price price of the food item
     * @param surplus boolean value for surplus food item
     * @param listingType listing type of the food item
     */
    public FoodItem(String name, Date expirationDate, double price, boolean surplus, String listingType) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.price = price;
        this.surplus = surplus;
        this.listingType = listingType;
    }

    public FoodItem() {
        //TODO Auto-generated constructor stub
    }

    /**
     * Getter for Item Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for Id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Setter for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for expiration date
     * @return expiration date
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * setter for expiration date
     * @param expirationDate
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * getter for price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * setter for price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter for surplus
     * @return surplus
     */
    public boolean isSurplus() {
        return surplus;
    }

    /**
     * setter for surplus
     * @param surplus
     */
    public void setSurplus(boolean surplus) {
        this.surplus = surplus;
    }

    /**
     * getter for listing type
     * @return surplus
     */
    public String getListingType() {
        return listingType;
    }

    /**
     * setter for listing type
     * @param listingType
     */
    public void setListingType(String listingType) {
        this.listingType = listingType;
    }

    /**
     * getter for quantity of items
     * @return quantity
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * set the quatity
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
