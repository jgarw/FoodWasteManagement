package com.cst8288.finalproject.controller;


public interface PurchasedItemsDAO {

    /**
     * Method used to insert the purchased items by the consumer into the database
     * @param item_id
     * @param consumer_id
     * @param quantity
     */
    public void addPurchase(int item_id, String consumerEmail, int quantity);
}
