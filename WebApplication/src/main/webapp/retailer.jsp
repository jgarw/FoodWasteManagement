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
   <%@ include file="header.jsp" %>
        <h1>Food Items</h1>
        <a href="createItems.jsp"><button type="button" class="btn">Add Food Item</button></a>
        	<div class="retailerContainer">
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
	                    <th>Actions</th>
	                </tr>
	            </thead>
	            <tbody>
	                <%
	                    FoodItemsDAOImpl foodItemsDao = new FoodItemsDAOImpl();
	                	String retailerEmail = (String) session.getAttribute("username");
	
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

	                <tr>
	                    <td><%= item.getId() %></td>
	                    <td><%= item.getName() %></td>
	                    <td><%= item.getExpirationDate() %></td>
	                    <td><%= "$" + item.getPrice() %></td>
	                    <td><%= surplusString %></td>
	                    <td><%= item.getListingType() %></td>
	                    <td><%= item.getQuantity() %></td>
	                    <td>
	                        <a href="updateItem.jsp?id=<%= item.getId() %>">Update</a>
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
