<%@ page import="java.util.List, java.util.ArrayList, java.util.Arrays" %>
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
    <h1>Hello <%= request.getSession().getAttribute("username") %>!</h1>
    <br>
    <h2>Thank you for helping us battle food waste!</h2>
    <br>

    <h4>Claim Confirmation:</h4>
    <%
        String[] selectedItems = request.getParameterValues("selectedItems");

        if (selectedItems == null || selectedItems.length == 0) {
            out.println("<p>No items selected. Please select items to claim.</p>");
        } else {
            String organizationEmail = (String) request.getSession().getAttribute("username");

            FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
            ClaimedItemsDAOImpl claimedItemsDAO = new ClaimedItemsDAOImpl();
            List<String> selectedItemList = Arrays.asList(selectedItems);
            List<FoodItem> claimedItems = new ArrayList<>();

            for (String itemId : selectedItemList) {
                int itemIdInt = Integer.parseInt(itemId);
                int quantityToClaim = Integer.parseInt(request.getParameter("quantity_" + itemIdInt));

                // Retrieve the current quantity of the item from the database
                int currentQuantity = foodItemsDAO.getCurrentQuantity(itemIdInt);
                if (quantityToClaim <= currentQuantity) {
                    int newQuantity = currentQuantity - quantityToClaim;

                    // Retrieve the item details from the database
                    FoodItem item = foodItemsDAO.retrieveFoodItem(itemIdInt);

                    // Update the food item's quantity
                    foodItemsDAO.updateItemQuantity(itemIdInt, newQuantity);

                    // Record the claim in the claims table
                    claimedItemsDAO.addClaim(itemIdInt, organizationEmail, quantityToClaim);

                    // Add the item to the list of claimed items
                    item.setQuantity(quantityToClaim); // set the quantity claimed for display
                    claimedItems.add(item);
                }
            }
            
            // Display claimed items
            if (claimedItems.isEmpty()) {
    %>
        <p>No items were claimed.</p>
    <%
            } else {
    %>
        <h2>Items Claimed</h2>
        <table>
            <tr>
                <th>Item ID</th>
                <th>Quantity</th>
            </tr>
            <%
                for (FoodItem item : claimedItems) {
            %>
                <tr>
                    <td><%= item.getId() %></td>
                    <td><%= item.getQuantity() %></td>
                </tr>
            <%
                }
            %>
        </table>
        <p>Thank you for your claim!</p>
    <%
            }
        }
    %>

    <br>
    <h2>Claim History</h2>
    <%
    	ClaimedItemsDAOImpl claimedItemsDAO = new ClaimedItemsDAOImpl();   
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
