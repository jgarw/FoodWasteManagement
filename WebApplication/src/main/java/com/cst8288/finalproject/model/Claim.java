package com.cst8288.finalproject.model;

public class Claim {

	    /**
	     * claim ID
	     */
	     int claim_id;

	    /**
	     * id of the food Item
	     */
	    int item_id;

	    /**
	     * name of the food item
	     */
	    int organization_id;

	    /**
	     * quantity claimed
	     */
	    int quantity;

	    /**
	     * Constructor for Claimed Items
	     * @param claim_id
	     * @param item_id
	     * @param organization_id
	     * @param quantity
	     */
	    public Claim (int claim_id, int item_id, int organization_id, int quantity) {
	        this.claim_id =  claim_id;
	    	this.item_id = item_id;
	    	this.organization_id = organization_id;
	    	this.quantity = quantity;
	    }

	    /**
	     * constructor
	     */
	    public Claim(){

	    }

	    /**
	     * get claim ID
	     * @return
	     */
		 public int getClaim_id() {
	        return claim_id;
	    }

	     /**
	      * set purchase id
	      * @param claim_id
	      */
	    public void setClaim_id(int claim_id) {
	        this.claim_id = claim_id;
	    }

	    /*
	     * Getter for item id
	     */
	    public int getItem_id() {
	    	return item_id;
	    }

	    /**
	     * setter for item id
	     * @param item_id
	     */
	    public void setItem_id(int item_id) {
	    	this.item_id = item_id;
	    }

	    /*
	     * get the email of the organization
	     */
	    public int getConsumer_id() {
	    	return organization_id;
	    }

	    /**
	     * set the organization email
	     */
	    public void setConsumer_id(int organization_id) {
	    	this.organization_id = organization_id;
	    }

	    /**
	     * quantity of items claimed
	     * @return quantity
	     */
	    public int getQuantity() {
	        return quantity;
	    }


	    /**
	     * set the quantity
	     * @param quantity
	     */
	    public void setQuantity(int quantity) {
	        this.quantity = quantity;
	    }


	}


