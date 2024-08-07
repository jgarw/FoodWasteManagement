<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Retailer Page</title>
    </head>
    <body>
    <!-- Apply the pre-made header to the page -->
   <%@ include file="header.jsp" %>
        <h1>Food Items</h1>
        <!-- Create a button that will redirect the currently logged in Retailer to the createItems page -->
        <a href="createItems.jsp"><button type="button" class="btn">Add Food Item</button></a>
        	<!-- Container to hold information on Retailer page -->
        	<div class="retailerContainer">
        	
        	<!-- Table that will show all of the food items associated with the currently logged in retailer -->
	        <table border="1" id="retailerTable">
	            <thead>
	                <tr>
	                    <th>ID</th>
	                    <th>Name</th>
	                    <th>Expiration Date</th>
	                    <th>Price</th>
	                    <th>Surplus</th>
	                    <th>Listing Type</th>
	                    <th>Quantity</th>
	                    <th colspan="2">Actions</th>
	                </tr>
	            </thead>
	            <tbody>
	                <%
	                	//instantiate a FoodItemsDAOImpl to retrieve food items from the FoodItems MySQL table
	                    FoodItemsDAOImpl foodItemsDao = new FoodItemsDAOImpl();
	                	String retailerEmail = (String) session.getAttribute("username");
	
	                	// create a list of all FoodItems associated with the current Retailer's email
	                    List<FoodItem> foodItems = foodItemsDao.retrieveAllFoodItems(retailerEmail);
	                    
	                    // Checks if the food items database table contains any food items, and displays them only if it does
	                    if (foodItems != null) {
	                    	// Displays the attributes of each item in each row of the table
	                        for (FoodItem item : foodItems) {
	                        	String surplusString = "";
	                        	
	                        	// Displays surplus as "Yes", "No" rather than boolean values
	                        	if (item.isSurplus()) {
			                    	surplusString = "Yes";
			                    }
			                    else if (!item.isSurplus()) {
			                    	surplusString = "No";
			                    }
			                    else {
			                    	surplusString = "Error";
			                    }
	                        	
	                %>
					
					<!-- Create a row for each fooditem -->
	                <tr>
	                	<!-- Display the current FoodItem's ID -->
	                    <td><%= item.getId() %></td>
	                    <!-- Display the current FoodItem's name -->
	                    <td><%= item.getName() %></td>
	                    <!-- Display the current FoodItem's expiration date -->
	                    <td><%= item.getExpirationDate() %></td>
	                    <!-- Display the current FoodItem's price -->
	                    <td><%= "$" + item.getPrice() %></td>
	                    <!-- Display the current FoodItem's surplus status -->
	                    <td><%= surplusString %></td>
	                    <!-- Display the current FoodItem's listing type (donation or discount) -->
	                    <td><%= item.getListingType() %></td>
	                    <!-- Display the current FoodItem's quantity -->
	                    <td><%= item.getQuantity() %></td>
	                    
	                    <!-- Create a button for updating and deleting FoodItems -->
	                    <td>
	                        <a href="updateItem.jsp?id=<%= item.getId() %>">Update</a>
	                    </td>
	                    <td>
	                        <a href="deleteItem.jsp?id=<%= item.getId() %>">Delete</a>
	                    </td>
	                </tr>
	                <%
	                        }
	                    }
	                %>
	            </tbody>
	        </table>
	     </div>
    </body>
</html>
