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
	<!-- Link the already existing header file -->
    <%@ include file="header.jsp" %>

    <div class="content">
    	<!-- Place all items inside a container -->
        <div class="container">
        	
        	<!-- Welcome the logged in consumer -->
            <h1>Welcome, <%= request.getSession().getAttribute("name") %>!</h1>
            <h2>Discounted Food Items</h2>
			
			<!-- On form submission, redirect to processPurchase page to process the purchase made by consumer -->
            <form action="processPurchase.jsp" method="post">
            	
            	<!-- Create a table with headers containing Name, expiration date, price, 
            		 available quantity, and quantity to purchase -->
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Expiration Date</th>
                        <th>Price</th>
                        <th>Available</th>
                        <th>Quantity</th>
                    </tr>
                    <%
                    	// Instantiate a FoodItemsDAOImpl for retrieving items from FoodItems table in MySQL database
                        FoodItemsDAOImpl foodItemsDAO = new FoodItemsDAOImpl();
                    	
                    	// create a list of all items in FoodItems table that are marked as 'Discounted'
                        List<FoodItem> foodItemsList = foodItemsDAO.retrieveDiscountedItems();
   
						// if the FoodItems list is empty, display a message to the user that no items are available
                        if (foodItemsList.isEmpty()) {
                            out.println("<tr><td colspan='4'>No food items available for discount at the moment. Please check back later.</td></tr>");
                        }
						
						/* 
							if the list is not empty, iterate through the list and create a FoodItem object at each index with the values from the list.
							then display the items to the user in a table 
						*/
                        else {
                            for (FoodItem item : foodItemsList) {
                    %>
                    <!-- create a row for each item retrieved  -->
                    <tr>
                    	<!-- Display the items name -->
                        <td><%= item.getName() %></td>
                        <!-- Display the items expiration date -->
                        <td id="expDate"><%= item.getExpirationDate() %></td>
                        <!-- Display the items price -->
                        <td>$<%= item.getPrice() %></td>
                        <!-- Display the items quantity -->
                        <td><%= item.getQuantity() %>
                        
                        <!-- 
                        	 provide an number input field for user to specify desired quantity of food to purchase.
                        	 Whenever the quantity changes, call the JavaScript function to calculate total price of items
                        -->
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
	
	<!-- Create a JavaScript function to calculate total cost of items selected -->
    <script>
        function calculateTotal() {
            var total = 0;
            var items = document.getElementsByClassName("item");
			
            // Iterate through the items selected and calculate the total price, adding the new total to the previous each time
            for (var i = 0; i < items.length; i++) {
                var price = parseFloat(items[i].getAttribute("data-price"));
                var quantity = parseInt(items[i].value);
                total += price * quantity;
            }

            document.getElementById("totalPrice").innerText = "Total Price: $" + total.toFixed(2);
        }
		
        //calculate initial total (hopefully $0) on page load
        window.onload = function() {
            calculateTotal();
        };
    </script>
</body>
</html>

