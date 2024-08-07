<%@ page import="java.util.List, java.util.ArrayList, java.util.Enumeration" %>
<%@ page import="com.cst8288.finalproject.model.FoodItem" %>
<%@ page import="com.cst8288.finalproject.model.Purchase" %>
<%@ page import="com.cst8288.finalproject.controller.FoodItemsDAOImpl" %>
<%@ page import="com.cst8288.finalproject.controller.PurchasedItemsDAOImpl" %>
<%@ page import="com.cst8288.finalproject.controller.ConsumerDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Purchase Confirmation</title>
</head>
<body>
<%
    request.setAttribute("backUrl", "consumer.jsp");
%>
<%@ include file="header.jsp" %>
<!-- add all items to container-->
<div class="container">
    <h1>Hello <%= request.getSession().getAttribute("name") %>!</h1>
    <br>
    <h2>Thank you for helping us battle food waste!</h2>
    <br>

    <h1>Purchase Confirmation</h1>

    <%
        // Retrieve session and form parameters
        String consumerEmail = (String) session.getAttribute("username");
        Enumeration<String> parameterNames = request.getParameterNames();

        // Instantiate DAOs for database operations on food items and purchased items
        FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
        PurchasedItemsDAOImpl purchasedItemsDAO = new PurchasedItemsDAOImpl();

        // Initialize variables to store purchased items and total price
        List<FoodItem> purchasedItems = new ArrayList<>();
        double totalPrice = 0;

        // Loop through the parameters to find quantities
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("quantity_")) {
                // Extract the item ID and quantity from the parameter name
                int itemId = Integer.parseInt(paramName.split("_")[1]);
                String quantityStr = request.getParameter(paramName);
                int quantity = Integer.parseInt(quantityStr);

                // Retrieve the item details from the database
                FoodItem item = foodItemsDAO.retrieveFoodItem(itemId);
                
                //quantity to buy must be greater than zero, and less than the total quantity of an item available
                if (item != null && quantity > 0 && quantity <= item.getQuantity()) {
                    double itemTotalPrice = quantity * item.getPrice();
                    totalPrice += itemTotalPrice;

                    // Update the item quantity in the database
                    int updatedQuantity = (item.getQuantity() - quantity);
                    foodItemsDAO.updateItemQuantity(itemId, updatedQuantity);
                    
                    // Record the purchase in the purchases table
                    purchasedItemsDAO.addPurchase(itemId, consumerEmail, quantity);

                    // Add the item to the list of purchased items
                    item.setQuantity(quantity); // set the quantity purchased for display
                    purchasedItems.add(item);
                }
            }
        }

        // check if the list of purchased items is empty and display a message to the user
        if (purchasedItems.isEmpty()) {
    %>
        <p>No items were purchased.</p>
    <%
        } 
        // if the list of purchased items is not empty, display the items and total price
        else {
    %>
        <h2>Items Purchased</h2>
        <!-- create a table to display the purchased items -->
        <table>
            <!-- create table headers -->
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
            </tr>
            <%
                // iterate through the list of purchased items and display them in the table
                for (FoodItem item : purchasedItems) {
            %>
                <tr>
                    <!-- display item name -->
                    <td><%= item.getName() %></td>
                    <!-- display item price -->
                    <td>$<%= item.getPrice() %></td>
                    <!-- display item quantity -->
                    <td><%= item.getQuantity() %></td>
                    <!-- display total price for the item -->
                    <td>$<%= item.getPrice() * item.getQuantity() %></td>
                </tr>
            <%
                }
            %>
        </table>
        <!-- display the total price for all purchased items -->
        <h3>Total Price: $<%= totalPrice %></h3>
        <p>Thank you for your purchase!</p>
    <%
        }
    %>

    <br>
    <!-- display purchase history related to consumer -->
    <h2>Purchase History</h2>
    <%
        // Retrieve purchase history from the database
        List<Purchase> purchaseHistory = purchasedItemsDAO.retrieveAllPurchases((String) request.getSession().getAttribute("username"));

        // check if the purchase history is empty and display a message to the user
        if (purchaseHistory.isEmpty()) {
    %>
        <p>No purchase history available.</p>
    <%
        } 
        // if the purchase history is not empty, display the history in a table
        else {
    %>
        <!-- create a table to display the purchase history -->
        <table>
            <!-- create table headers -->
            <tr>
                <th>Item ID</th>
                <th>Quantity</th>
            </tr>
            <%
                // iterate through the list of purchase history and display them in the table
                for (Purchase history : purchaseHistory) {
            %>
                <!-- display the item ID and quantity -->
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

<!-- include pre-made footer file-->
<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>
