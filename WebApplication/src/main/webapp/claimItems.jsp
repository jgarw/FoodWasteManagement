<%@ page import="java.util.List, java.util.ArrayList, java.util.Arrays" %>
<%@ page import="java.util.List, java.util.ArrayList, java.util.Enumeration" %>
<%@ page import="com.cst8288.finalproject.model.FoodItem" %>
<%@ page import="com.cst8288.finalproject.model.Claim" %>
<%@ page import="com.cst8288.finalproject.controller.FoodItemsDAOImpl" %>
<%@ page import="com.cst8288.finalproject.controller.ClaimedItemsDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <link rel="stylesheet" href="css/style.css">
	    <title>Claim Items</title>
	</head>
		<body>
			
			<!-- set the backUrl variable used by back button to the previous page -->
			<% request.setAttribute("backUrl", "organization.jsp"); %>
			<%@ include file="header.jsp" %>
			
			<!-- Display all information inside a container -->
			<div class="container">
				<!-- display welcome message to user by name -->
			    <h1>Hello <%= request.getSession().getAttribute("name") %>!</h1>
			    <br>
			    <h2>Thank you for helping us battle food waste!</h2>
			    <br>
				
				
			    <h4>Claim Confirmation:</h4>
			    <%
			    // Retrieve session and form parameters
			    String organizationEmail = (String) session.getAttribute("username");
			    Enumeration<String> parameterNames = request.getParameterNames();
			
			    /* 	create a new FoodItemsDAOImpl. 
			    	This will be used to update the quantity of items in FoodItems table when an item is claimed
			    */
			    FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
			    
			    /*
			    	create an instance of claimedItemDAOImpl.
			    	This will be used to store and retrieve information about items claimed by this Organization
			    	into the Claims table.
			    */
			    ClaimedItemsDAOImpl claimedItemsDAO = new ClaimedItemsDAOImpl();
				
			    // create a list of the items claimed by the currently logged in Organization
			    List<FoodItem> claimedItems = new ArrayList<>();
			    double totalPrice = 0;
			
			    // Loop through the parameters to find quantities
			    while (parameterNames.hasMoreElements()) {
			        String paramName = parameterNames.nextElement();
			        
			        // check for any paramaters starting with "quantity"
			        if (paramName.startsWith("quantity_")) {
			        	
			        	// store the ID retrieved from the tail end of parsed 'quantity_#' string as an integer
			            int itemId = Integer.parseInt(paramName.split("_")[1]);
			        	
			        	//store the beginning of the parsed string in a String variable as 'quantity'
			            String quantityStr = request.getParameter(paramName);
			        	//convert the newly created String variable holding quantity to an integer
			            int quantity = Integer.parseInt(quantityStr);
			            // Retrieve the item details from the database using itemID
			            FoodItem item = foodItemsDAO.retrieveFoodItem(itemId);
			            
			            //quantity to buy must be greater than zero, and less than the total quantity of an item available
			            if (item != null && quantity > 0 && quantity <= item.getQuantity()) {
			            	
			            	// calculate the total price of an item
			                double itemTotalPrice = quantity * item.getPrice();
			            	
			            	//add the total price of each item to the total price of the order
			                totalPrice += itemTotalPrice;
			
			                // Update the item quantity in the FoodItems table in database
			                int updatedQuantity = (item.getQuantity() - quantity);
			                foodItemsDAO.updateItemQuantity(itemId, updatedQuantity);
			                
			                // Record the purchase in the purchases table
			                claimedItemsDAO.addClaim(itemId, organizationEmail, quantity);
			
			                // Add the item to the list of claimed items
			                item.setQuantity(quantity); // set the quantity claimed for display
			                claimedItems.add(item);
			            }
			        }
			    }
			
			    // If there are no items in the claimedItems list, print a message
			    if (claimedItems.isEmpty()) {
			%>
			    <p>No items were claimed.</p>
			    
			<% 
				//if there are items in the list create a table with all the claimed items
			    } else { %>
			    <h2>Items Claimed</h2>
			    <table>
			        <tr>
			            <th>Name</th>
			            <th>Quantity</th>
			        </tr>
			        <%
			        	//iterate through the list of claimed items, creating an item object at each index
			            for (FoodItem item : claimedItems) {
			        %>
			        	<!-- display the name and quantity of items claimed in this purchase -->
			            <tr>
			                <td><%= item.getName() %></td>
			                <td><%= item.getQuantity() %></td>
			            </tr>
			        <%
			            }
			        %>
			    </table>
			    <p>Thank you for your purchase!</p>
			<%
			    }
			%>
			
			    <br>
			    <!-- Display the entire history of claimed items from this Organization -->
			    <h2>Claim History</h2>
			    <%
			    	// create a list of claim history using ClaimedItemsDAOImpl
			        List<Claim> claimHistory = claimedItemsDAO.retrieveAllClaims((String) request.getSession().getAttribute("username"));
					
			    	//if the list is empty ,display a message to the user
			        if (claimHistory.isEmpty()) {
			    %>
			        <p>No claim history available.</p>
			    <%	
			    	//if the list is not empty, display the list to the user
			        } else {
			    %>
			        <table>
			            <tr>
			                <th>Item ID</th>
			                <th>Quantity</th>
			            </tr>
			            <%	
			            	//iterate through the claim history, creating a Claim object at each index
			                for (Claim history : claimHistory) {
			            %>
			            	<!-- Display the item ID and quantity of the claimed items -->
			                <tr>
			                    <td><%= history.getItem_id() %></td>
			                    <td><%= history.getQuantity() %></td>
			                </tr>
			            <%
			                }
			            %>
			        </table>
			    <%
			        }
			    %>
			
			</div>
		
			<!-- Add footer to page -->
			<footer>
			    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
			</footer>
		</body>
</html>
