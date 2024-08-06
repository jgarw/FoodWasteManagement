package com.cst8288.finalproject.controller;

public interface ClaimedItemsDAO {

    /**
     * This method adds claimed items into the claims table for the database
     * @param item_id
     * @param organizationEmail
     * @param quantity
     */
    public void addClaim(int item_id, String organizationEmail, int quantity);
}
