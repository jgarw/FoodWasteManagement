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
<div class="container">
    <h1>Welcome, <%= request.getSession().getAttribute("userName") %>!</h1>
    <h2>Food Items Available for Donation</h2>

    <%
        FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
        List<FoodItem> foodItemsList = foodItemsDAO.retrieveAvailableDonations();

        if (foodItemsList.isEmpty()) {
    %>
        <p>No food items available for donation at the moment. Please check back later.</p>
    <%
        } else {
    %>
        <table>
            <tr>
                <th>Name</th>
                <th>Expiration Date</th>
                <th>Quantity</th>
            </tr>
            <%
                for (FoodItem item : foodItemsList) {
            %>
                <tr>
                    <td><%= item.getName() %></td>
                    <td><%= item.getExpirationDate() %></td>
                    <td><%= item.getQuantity() %></td>
                </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
</div>
</body>
</html>
