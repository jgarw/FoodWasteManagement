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
<%
    request.setAttribute("backUrl", "organization.jsp");
%>
<%@ include file="header.jsp" %>

<div class="container">
    <h1>Hello <%= request.getSession().getAttribute("name") %>!</h1>
    <br>
    <h2>Thank you for helping us battle food waste!</h2>
    <br>

    <h4>Claim Confirmation:</h4>
    <%
    // Retrieve session and form parameters
    String organizationEmail = (String) session.getAttribute("username");
    Enumeration<String> parameterNames = request.getParameterNames();

    FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
    ClaimedItemsDAOImpl claimedItemsDAO = new ClaimedItemsDAOImpl();

    List<FoodItem> claimedItems = new ArrayList<>();
    double totalPrice = 0;

    // Loop through the parameters to find quantities
    while (parameterNames.hasMoreElements()) {
        String paramName = parameterNames.nextElement();
        if (paramName.startsWith("quantity_")) {
            int itemId = Integer.parseInt(paramName.split("_")[1]);
            String quantityStr = request.getParameter(paramName);
            int quantity = Integer.parseInt(quantityStr);
            // Retrieve the item details from the database
            FoodItem item = foodItemsDAO.retrieveFoodItem(itemId);
            System.out.println("Retrieved item: " + item.getName() + " with available quantity: " + item.getQuantity());
            
            //quantity to buy must be greater than zero, and less than the total quantity of an item available
            if (item != null && quantity > 0 && quantity <= item.getQuantity()) {
                double itemTotalPrice = quantity * item.getPrice();
                totalPrice += itemTotalPrice;

                // Update the item quantity in the database
                
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

    // Display claimed items and total price
    if (claimedItems.isEmpty()) {
%>
    <p>No items were claimed.</p>
<%
    } else {
%>
    <h2>Items Claimed</h2>
    <table>
        <tr>
            <th>Name</th>
            <th>Quantity</th>
        </tr>
        <%
            for (FoodItem item : claimedItems) {
        %>
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
    <h2>Claim History</h2>
    <%
        List<Claim> claimHistory = claimedItemsDAO.retrieveAllClaims((String) request.getSession().getAttribute("username"));

        if (claimHistory.isEmpty()) {
    %>
        <p>No claim history available.</p>
    <%
        } else {
    %>
        <table>
            <tr>
                <th>Item ID</th>
                <th>Quantity</th>
            </tr>
            <%
                for (Claim history : claimHistory) {
            %>
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

<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>
