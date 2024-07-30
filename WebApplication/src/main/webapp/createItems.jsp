<%@ page import="java.sql.*" %>
<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Create an item</title>
    </head>
    <body>
        <h1>Create a New Item</h1>
        <form action="createItems.jsp" method="post">
            <label for="itemName">Item Name:</label>
            <input type="text" id="itemName" name="itemName" required><br><br>

            <label for="expirationDate">Expiration Date:</label>
            <input type="date" id="expirationDate" name="expirationDate" required><br><br>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" required><br><br>

            <label>Surplus:</label>
            <input type="radio" id="surplusYes" name="surplus" value="yes">
            <label for="surplusYes">Yes</label>
            <input type="radio" id="surplusNo" name="surplus" value="no">
            <label for="surplusNo">No</label><br><br>

            <% if ("yes".equals(request.getParameter("surplus"))) { %>
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
                String expirationDate = request.getParameter("expirationDate");
                String price = request.getParameter("price");
                String surplus = request.getParameter("surplus");
                String listingType = request.getParameter("listingType");
                String discountPrice = request.getParameter("discountPrice");
                String retailerEmail = session.getAttribute("email");

                // Add your processing logic here (e.g., save to database, validate, etc.)
                FoodItemsDAOImpl dao = new FoodItemsDAOImpl();
                dao.addFoodItem(itemName, expirationDate, price, surplus, listingType, retailerEmail);

                // Example output
                out.println("<h2>Item Created Successfully</h2>");
                out.println("<p>Item Name: " + itemName + "</p>");
                out.println("<p>Expiration Date: " + expirationDate + "</p>");
                out.println("<p>Price: " + price + "</p>");
                out.println("<p>Surplus: " + surplus + "</p>");
                if ("yes".equals(surplus)) {
                    out.println("<p>Listing Type: " + listingType + "</p>");
                    out.println("<p>Discount Price: " + discountPrice + "</p>");
                }
            }
        %>
    </body>
</html>
