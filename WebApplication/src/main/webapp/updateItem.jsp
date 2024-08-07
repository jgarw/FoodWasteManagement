<%@ page import="java.sql.*" %>
<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="css/style.css">
		<title>Update Page</title>
	</head>
	<body>
	<%
    request.setAttribute("backUrl", "retailer.jsp");
%>
	<!-- include pre-made header file-->
	 <%@ include file="header.jsp" %>
		<!-- add all items to container-->
		<div class="container">
			<h1>Update Item</h1>
			<%	
				// Retrieve the item ID from the request and store it in a variable
		        String itemId = request.getParameter("id");
				int id = 0;

				// Instantiate a FoodItemsDAOImpl object
		        FoodItemsDAOImpl dao = new FoodItemsDAOImpl();

				// create a FoodItem object and set it to null
		        FoodItem item = null;
		        
				// Check if item ID is not null
		        if (itemId != null) {
					// Parse the item ID to an integer
		            id = Integer.parseInt(itemId);
					// Retrieve the item from the database
		            item = dao.retrieveFoodItem(id);
		        }
		
		        // Check if item is found
		        if (item != null) {
			    	%>
					<!-- create a form to allow the retailer to update the item -->
			        <form method="post">	
						<!-- create input field for item name -->		
				        <label for="itemName">Item Name:</label>
				        <input type="text" id="itemName" name="itemName" value="<%= item.getName() %>" required><br><br>
						
						<!-- create input field for item expiration date -->
				        <label for="expirationDate">Expiration Date:</label>
				        <input type="date" id="expirationDate" name="expirationDate" value="<%= item.getExpirationDate() %>" required><br><br>
				        
						<!-- create input field for item quantity -->
				        <label for="quantity">Quantity:</label>
	            		<input type="number" id="quantity" name="quantity" step="1" value= <%= item.getQuantity() %>><br><br>
				
						<!-- create input field for item price -->
				        <label for="price">Price:</label>
				        <input type="number" id="price" name="price" step="0.01" value="<%= item.getPrice() %>" required><br><br>
				
						<!-- create radio buttons for surplus -->
				        <label>Surplus:</label>
				        <input type="radio" id="surplusYes" name="surplus" value="true" <%= item.isSurplus() ? "checked" : "" %>>
				        <label for="surplusYes">Yes</label>
				        <input type="radio" id="surplusNo" name="surplus" value="false" <%= !item.isSurplus() ? "checked" : "" %>>
				        <label for="surplusNo">No</label><br><br>
						
						<!-- create radio buttons for listing type -->
				        <% if (item.isSurplus()) { %>
				            <label>Listing Type:</label>
				            <input type="radio" id="discounted" name="listingType" value="discounted" <%= "discounted".equals(item.getListingType()) ? "checked" : "" %>>
				            <label for="discounted">Discounted</label>
				            <input type="radio" id="donation" name="listingType" value="donation" <%= "donation".equals(item.getListingType()) ? "checked" : "" %>>
				            <label for="donation">Donation</label><br><br>
				        <% } %>
							
				        <input type="submit" value="Update">
				    </form>
				<% } %>
		    
			<%
	            if ("POST".equalsIgnoreCase(request.getMethod())) {
					// Retrieve the item name from the request and store it in a variable
	                String itemName = request.getParameter("itemName");

					// Retrieve the expiration date from the request and store it in a variable
	                Date expirationDate = Utility.parseDate(request.getParameter("expirationDate"));

					// Retrieve the quantity from the request and store it in a variable
	                int quantity = Integer.parseInt(request.getParameter("quantity"));

					// Retrieve the price from the request and store it in a variable
	                double price = Double.parseDouble(request.getParameter("price"));

					// Retrieve the surplus from the request and store it in a variable
	                boolean surplus = Boolean.parseBoolean(request.getParameter("surplus"));

					// Retrieve the listing type from the request and store it in a variable
	                String listingType = request.getParameter("listingType");

					// Retrieve the retailer email from the session and store it in a variable
	                String retailerEmail = (String) session.getAttribute("username");

					// Initialize a boolean variable to store the validity of the input
	                boolean isValid = true;

	                // Validate the input
	                if (!surplus && !"regular".equals(listingType)) {
	                    isValid = false;
	                    out.print("<p style='color:red;'>Invalid Input: Only surplus items can be discount or donation.</p>");
	                }

	                // If valid, process the form
	                if (isValid) {
	                    dao.updateFoodItem(id, itemName, expirationDate, quantity, price, surplus, listingType, retailerEmail);
		                RetailerSubject subject = new RetailerSubject();
		                
		                //if surplus is set to true, notify food alerts subscribers
	                	if(surplus == true){
		                	subject.notifyObservers("Hot new food alert in your area! " + session.getAttribute("name") + " marked " + itemName + " as surplus at " + price);
		                }
		                response.sendRedirect("retailer.jsp");
	                } 
	            }
	        %>
		</div>
	</body>
</html>