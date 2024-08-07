package com.cst8288.finalproject.controller;

/**
 * Interface that will allow observers (clients) to track changes to subjects (fooditems)
 */
public interface Observer {

	/**
	 * Create method that will be implemented by concrete observer to receive notifications
	 * @param message
	 */
	void update(String message);

}
