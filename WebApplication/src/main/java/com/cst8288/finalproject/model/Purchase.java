package com.cst8288.finalproject.model;

public class Purchase {

    /**
     * purchase ID
     */
     int purchase_id;

    /**
     * id of the food Item
     */
    int item_id;

    /**
     * name of the food item
     */
    int consumer_id;

    /**
     * quantity purchased
     */
    int quantity;

    /**
     * Constructor for Purchased Items
     * @param purchase_id
     * @param item_id
     * @param consumer_id
     * @param quantity
     */
    public Purchase (int purchase_id, int item_id, int consumer_id, int quantity) {
        this.purchase_id =  purchase_id;
    	this.item_id = item_id;
    	this.consumer_id = consumer_id;
    	this.quantity = quantity;
    }

    /**
     * constructor
     */
    public Purchase(){

    }

    /**
     * get purchase ID
     * @return
     */
	 public int getPurchase_id() {
        return purchase_id;
    }

     /**
      * set purcase id
      * @param purchase_id
      */
    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
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
     * get the email of the consumer
     */
    public int getConsumer_id() {
    	return consumer_id;
    }

    /**
     * set the consumer email
     */
    public void setConsumer_id(int consumer_id) {
    	this.consumer_id = consumer_id;
    }

    /**
     * quantity of items purchased
     * @return quantity
     */
    public int getQuantity() {
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

