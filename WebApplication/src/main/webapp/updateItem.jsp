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
	 <%@ include file="header.jsp" %>
		<div class="container">
			<h1>Update Item</h1>
			<%
		        String itemId = request.getParameter("id");
				int id = 0;
		        FoodItemsDAOImpl dao = new FoodItemsDAOImpl();
		        FoodItem item = null;
		        
		        if (itemId != null) {
		            id = Integer.parseInt(itemId);
		            item = dao.retrieveFoodItem(id);
		        }
		
		        // Check if item is found
		        if (item != null) {
			    	%>
			        <form method="post">			
				        <label for="itemName">Item Name:</label>
				        <input type="text" id="itemName" name="itemName" value="<%= item.getName() %>" required><br><br>
				
				        <label for="expirationDate">Expiration Date:</label>
				        <input type="date" id="expirationDate" name="expirationDate" value="<%= item.getExpirationDate() %>" required><br><br>
				        
				        <label for="quantity">Quantity:</label>
	            		<input type="number" id="quantity" name="quantity" step="1" value= <%= item.getQuantity() %>><br><br>
				
				        <label for="price">Price:</label>
				        <input type="number" id="price" name="price" step="0.01" value="<%= item.getPrice() %>" required><br><br>
				
				        <label>Surplus:</label>
				        <input type="radio" id="surplusYes" name="surplus" value="true" <%= item.isSurplus() ? "checked" : "" %>>
				        <label for="surplusYes">Yes</label>
				        <input type="radio" id="surplusNo" name="surplus" value="false" <%= !item.isSurplus() ? "checked" : "" %>>
				        <label for="surplusNo">No</label><br><br>
				
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
	                String itemName = request.getParameter("itemName");
	                Date expirationDate = Utility.parseDate(request.getParameter("expirationDate"));
	                int quantity = Integer.parseInt(request.getParameter("quantity"));
	                double price = Double.parseDouble(request.getParameter("price"));
	                boolean surplus = Boolean.parseBoolean(request.getParameter("surplus"));
	                String listingType = request.getParameter("listingType");
	                String retailerEmail = (String) session.getAttribute("username");
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