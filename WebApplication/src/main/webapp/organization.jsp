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
<%@ include file="header.jsp" %>
<div class="content">
<div class="container">
    <h1>Welcome, <%= request.getSession().getAttribute("userName") %>!</h1>
    <h2>Food Items Available for Donation</h2>

    <form method="post" action="claimItems.jsp">
        <table>
            <tr>
                <th>Select</th>
                <th>Name</th>
                <th>Expiration Date</th>
                <th>Listing Type</th>
                <th>Available Quantity</th>
                <th>Claim Quantity</th>
            </tr>
            <%
            FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
            List<FoodItem> foodItemsList = foodItemsDAO.retrieveAvailableDonations();

            if (foodItemsList.isEmpty()) {
                out.println("<tr><td colspan='5'>No food items available for discount at the moment. Please check back later.</td></tr>");
            } else {
                for (FoodItem item : foodItemsList) {
            %>
                <tr>
                    <td><input type="checkbox" name="selectedItems" value="<%= item.getId() %>"></td>
                    <td><%= item.getName() %></td>
                    <td><%= item.getExpirationDate() %></td>
                    <td><%= item.getListingType() %></td>
                    <td><%= item.getQuantity() %></td>
                    <td><input type="number" name="quantity_<%= item.getId() %>" min="1" max="<%= item.getQuantity() %>" value="1"></td>
                </tr>
            <%
                }
            }
            %>
          </table>
    </form>
        <button type="submit">Claim Items</button>
</div>
</div>
<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>
