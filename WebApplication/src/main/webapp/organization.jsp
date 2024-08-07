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
    <h1>Welcome, <%= request.getSession().getAttribute("name") %>!</h1>
    <h2>Food Items Available for Donation</h2>

    <form action="claimItems.jsp" method="post" >
        <table>
            <tr>
                <th>Name</th>
                <th>Expiration Date</th>
                <th>Available Quantity</th>
                <th>Claim Quantity</th>
            </tr>
            <%
            FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
            List<FoodItem> foodItemsList = foodItemsDAO.retrieveAvailableDonations();

            if (foodItemsList.isEmpty()) {
                out.println("<tr><td colspan='6'>No food items available for donation at the moment. Please check back later.</td></tr>");
            } else {
                for (FoodItem item : foodItemsList) {
            %>
                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getExpirationDate() %></td>
                    <td><%= item.getQuantity() %></td>
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
<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>
