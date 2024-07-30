package com.cst8288.finalproject.controller;

public interface Observer {
	
	/**
	 * Create method that will be implemented by concrete observer to recieve notifications
	 * @param message
	 */
	void update(String message);
	
}
