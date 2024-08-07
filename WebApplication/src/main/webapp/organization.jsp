<%@ page import="java.util.List" %>
<%@ page import="com.cst8288.finalproject.model.FoodItem" %>
<%@ page import="com.cst8288.finalproject.controller.FoodItemsDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Organization Dashboard</title>
</head>
<body>
<!-- include pre-made header file-->
<%@ include file="header.jsp" %>
<div class="content">
<!-- add all items to container-->
<div class="container">
    <h1>Welcome, <%= request.getSession().getAttribute("name") %>!</h1>
    <h2>Food Items Available for Donation</h2>
    <!-- create a form to allow the organization to claim items -->
    <form action="claimItems.jsp" method="post" >
        <!-- create a table to display the available food items -->
        <table>
            <!-- create table headers -->
            <tr>
                <th>Name</th>
                <th>Expiration Date</th>
                <th>Available Quantity</th>
                <th>Claim Quantity</th>
            </tr>
            <%
            // instantiate a FoodItemsDAOImpl object
            FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();

            // retrieve all available food items from the database
            List<FoodItem> foodItemsList = foodItemsDAO.retrieveAvailableDonations();

            // check if the list is empty, if so, display a message to the user
            if (foodItemsList.isEmpty()) {
                out.println("<tr><td colspan='6'>No food items available for donation at the moment. Please check back later.</td></tr>");
            } else {
                // iterate through the list of food items and display them in the table
                for (FoodItem item : foodItemsList) {
            %>  
                <tr>
                    <!-- display item name -->
                    <td><%= item.getName() %></td>
                    <!-- display item expiration date -->
                    <td><%= item.getExpirationDate() %></td>
                    <!-- display item quantity -->
                    <td><%= item.getQuantity() %></td>
                    <!-- create input field for organization to claim quantity -->
                    <td><input type="number" name="quantity_<%= item.getId() %>" min="0" max="<%= item.getQuantity() %>" value="0"></td>
                </tr>
            <%
                }
            }
            %>
          </table>
          
          <button type="submit">Claim Items</button>
          
    </form>
</div>
</div>
<!-- include pre-made footer file-->
<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>
