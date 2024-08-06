<%@ page import="java.sql.*" %>
<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page import="java.sql.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Create an item</title>
    </head>
    <body>
    	<div class="container">
	        <h1>Create a New Item</h1>
	        <form action="createItems.jsp" method="post">
	            <label for="itemName">Item Name:</label><br>
	            <input type="text" id="itemName" name="itemName" required><br><br>
	
	            <label for="expirationDate">Expiration Date:</label>
	            <input type="date" id="expirationDate" name="expirationDate" required><br><br>
	            
	            <label for="quantity">Quantity:</label>
	            <input type="number" id="quantity" name="quantity" step="1" required><br><br>
	
	            <label for="price">Price:</label>
	            <input type="number" id="price" name="price" step="0.01" required><br><br>
	
	            <label>Surplus:</label>
	            <input type="radio" id="surplusYes" name="surplus" value="true">
	            <label for="surplusYes">Yes</label>
	            <input type="radio" id="surplusNo" name="surplus" value="false">
	            <label for="surplusNo">No</label><br><br>
	
	            <% if ("true".equals(request.getParameter("surplus"))) { %>
	                <label>Listing Type:</label>
	                <input type="radio" id="discounted" name="listingType" value="discounted">
	                <label for="discounted">Discounted</label>
	                <input type="radio" id="donation" name="listingType" value="donation">
	                <label for="donation">Donation</label><br><br>
	
	                <label for="discountPrice">Discount Price:</label>
	                <input type="number" id="discountPrice" name="discountPrice" step="0.01"><br><br>
	            <% } %>
	
	            <input type="submit" value="Create">
	        </form>
	
	        <%-- Processing form data if the request is a POST --%>
	        <%-- 
	        TODO:
	        implement logic to retrieve the email of the retailer that is logged in (sessions???)
	        use the FoodItemsDAOImpl addFoodItem method to add the food item to the database
	        --%>
	        <%
	            if ("POST".equalsIgnoreCase(request.getMethod())) {
	                String itemName = request.getParameter("itemName");
	                int quantity = Integer.parseInt(request.getParameter("quantity"));
	                Date expirationDate = Utility.parseDate(request.getParameter("expirationDate"));
	                double price = Double.parseDouble(request.getParameter("price"));
	                boolean surplus = Boolean.parseBoolean(request.getParameter("surplus"));
	                String listingType = request.getParameter("listingType");
	                String discountPrice = request.getParameter("discountPrice");
	                String retailerEmail = (String) session.getAttribute("username");
	
	                FoodItemsDAOImpl dao = new FoodItemsDAOImpl();
	                dao.addFoodItem(itemName, expirationDate, quantity, price, surplus, listingType, retailerEmail);
	                response.sendRedirect("retailer.jsp");
	            }
	        %>
		</div>
    </body>
</html>
