<%@ page import="com.cst8288.finalproject.controller.FoodItemsDAOImpl" %>
<%@ page import="com.cst8288.finalproject.controller.ClaimedItemsDAOImpl" %>
<%@ page import="java.util.List, java.util.Arrays" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Claim Items</title>
</head>
<body>
<div class="container">
    <h1>Hi <%= request.getSession().getAttribute("userName") %>!</h1>
    <br>
    <h2>Thank you for helping us battle food waste!</h2>
    <br>

    <h4>Confirmation: </h4>
    <%
        String[] selectedItems = request.getParameterValues("selectedItems");

        if (selectedItems == null || selectedItems.length == 0) {
            out.println("<p>No items selected. Please select items to claim.</p>");
        } else {
            String organizationEmail = (String) request.getSession().getAttribute("userEmail");

            FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
            ClaimedItemsDAOImpl claimedItemsDAO = new ClaimedItemsDAOImpl();
            List<String> selectedItemList = Arrays.asList(selectedItems);

            for (String itemId : selectedItemList) {
                int itemIdInt = Integer.parseInt(itemId);
                int quantityToClaim = Integer.parseInt(request.getParameter("quantity_" + itemIdInt));

                // Retrieve the current quantity of the item from the database
                int currentQuantity = foodItemsDAO.getCurrentQuantity(itemIdInt);
                int newQuantity = currentQuantity - quantityToClaim;

                // Update the food item's quantity
                foodItemsDAO.updateItemQuantity(itemIdInt, newQuantity);

                // Record the claim in the claims table
                claimedItemsDAO.addClaim(itemIdInt, organizationEmail, quantityToClaim);
            }
            out.println("<p>Items successfully claimed.</p>");
        }
    %>
    <a href="organization.jsp">Go Back</a>
</div>
</body>
</html>
