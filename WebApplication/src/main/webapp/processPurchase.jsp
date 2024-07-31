<%@ page import="java.util.List, java.util.ArrayList, java.util.Enumeration" %>
<%@ page import="com.cst8288.finalproject.model.FoodItem" %>
<%@ page import="com.cst8288.finalproject.controller.FoodItemsDAOImpl" %>
<%@ page import="com.cst8288.finalproject.controller.PurchasedItemsDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Purchase Confirmation</title>
</head>
<body>
<div class="container">
    <h1>Purchase Confirmation</h1>

    <%
        // Retrieve session and form parameters
        String consumerEmail = (String) session.getAttribute("userEmail");
        Enumeration<String> parameterNames = request.getParameterNames();

        FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
        PurchasedItemsDAOImpl purchasedItemsDAO = new PurchasedItemsDAOImpl();

        List<FoodItem> purchasedItems = new ArrayList<>();
        double totalPrice = 0;

        // Loop through the parameters to find quantities
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("quantity_")) {
                int itemId = Integer.parseInt(paramName.split("_")[1]);
                int quantity = Integer.parseInt(request.getParameter(paramName));

                // Retrieve the item details from the database
                FoodItem item = foodItemsDAO.retrieveFoodItemById(itemId);
                if (item != null && quantity <= item.getQuantity()) {
                    double itemTotalPrice = quantity * item.getPrice();
                    totalPrice += itemTotalPrice;

                    // Update the item quantity in the database
                    foodItemsDAO.updateItemQuantity(itemId, item.getQuantity() - quantity);
                    
                    // Record the purchase in the purchases table
                    purchasedItemsDAO.addPurchase(itemId, consumerEmail, quantity);

                    // Add the item to the list of purchased items
                    item.setQuantity(quantity); // set the quantity purchased for display
                    purchasedItems.add(item);
                }
            }
        }

        // Display purchased items and total price
        if (purchasedItems.isEmpty()) {
    %>
        <p>No items were purchased.</p>
    <%
        } else {
    %>
        <h2>Items Purchased</h2>
        <table>
            <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Total Price</th>
            </tr>
            <%
                for (FoodItem item : purchasedItems) {
            %>
                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getPrice() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td><%= item.getPrice() * item.getQuantity() %></td>
                </tr>
            <%
                }
            %>
        </table>
        <h3>Total Price: <%= totalPrice %></h3>
        <p>Thank you for your purchase!</p>
    <%
        }
    %>
</div>
</body>
</html>
