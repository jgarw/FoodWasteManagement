<%@ page import="java.util.List" %>
<%@ page import="com.cst8288.finalproject.model.FoodItem" %>
<%@ page import="com.cst8288.finalproject.controller.FoodItemsDAOImpl" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Consumer Dashboard</title>
</head>
<body>
    <%@ include file="header.jsp" %>

    <div class="content">
        <div class="container">
            <h1>Welcome, <%= request.getSession().getAttribute("name") %>!</h1>
            <h2>Discounted Food Items</h2>

            <form action="processPurchase.jsp" method="post">
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Expiration Date</th>
                        <th>Price</th>
                        <th>Quantity</th>
                    </tr>
                    <%
                        FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
                        List<FoodItem> foodItemsList = foodItemsDAO.retrieveDiscountedItems();
   

                        if (foodItemsList.isEmpty()) {
                            out.println("<tr><td colspan='4'>No food items available for discount at the moment. Please check back later.</td></tr>");
                        } else {
                            for (FoodItem item : foodItemsList) {
                    %>
                    <tr>
                        <td><%= item.getName() %></td>
                        <td id="expDate"><%= item.getExpirationDate() %></td>
                        <td>$<%= item.getPrice() %></td>
                        <td>
                            <input type="number" name="quantity_<%= item.getId() %>" 
                                   min="0" max="<%= item.getQuantity() %>" value="0" 
                                   class="item" data-price="<%= item.getPrice() %>"
                                   onchange="calculateTotal()">
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </table>
                <p id="totalPrice"></p>
                <button type="submit">Purchase</button>
            </form>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
    </footer>

    <script>
        function calculateTotal() {
            var total = 0;
            var items = document.getElementsByClassName("item");

            for (var i = 0; i < items.length; i++) {
                var price = parseFloat(items[i].getAttribute("data-price"));
                var quantity = parseInt(items[i].value);
                total += price * quantity;
            }

            document.getElementById("totalPrice").innerText = "Total Price: $" + total.toFixed(2);
        }

        window.onload = function() {
            calculateTotal();
        };
    </script>
</body>
</html>

