<%@ page import="java.sql.*" %>
<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="css/style.css">
        <title>Create an item</title>
    </head>
    <body>
		<!-- set the backUrl attribute to the retailer.jsp page -->
    	<% request.setAttribute("backUrl", "retailer.jsp"); %>
		<!-- include the pre-made header file -->
	 <%@ include file="header.jsp" %>
		<!-- place items inside container -->
    	<div class="container">
	        <h1>Create a New Item</h1>
			<!-- create a form to allow the retailer to input the item's information -->
	        <form action="createItems.jsp" method="post">
	        <table>
				<tr>
					<td>
						<!-- create input field for item name -->
			            <label for="itemName">Item Name:</label><br>
			            <input type="text" id="itemName" name="itemName" required><br><br>
		            </td>
	            </tr>
	            
	            <tr>
					<td>
						<!-- create input field for expiration date -->
			            <label for="expirationDate">Expiration Date:</label>
			            <input type="date" id="expirationDate" name="expirationDate" required><br><br>
	            	</td>
	            </tr>
	            
	            <tr>
					<td>
			            <!-- create input field for quantity -->
			            <label for="quantity">Quantity:</label>
			            <input type="number" id="quantity" name="quantity" step="1" required><br><br>
			        </td>
	            </tr>
	            
	            <tr>
					<td>
						<!-- create input field for price -->
			            <label for="price">Price:</label>
			            <input type="number" id="price" name="price" step="0.01" required><br><br>
		            </td>
	            </tr>
				
				<tr>
					<td>
						<!-- create radio buttons for surplus -->
			            <label>Surplus:</label>
			            <input type="radio" id="surplusYes" name="surplus" value="true">
			            <label for="surplusYes">Yes</label>
			            <input type="radio" id="surplusNo" name="surplus" value="false">
			            <label for="surplusNo">No</label><br><br>
			       </td>
	            </tr>
					
					<!-- Create drowndown menu for listing types -->
                 <tr>
                 	<td>
		                 <label>Listing Type:</label>
		                <select name="listingType" id="itemName" required>
		                        <option value="regular">Regular</option>
		                        <option value="discount">Discount</option>
		                        <option value="donation">Donation</option>
		                    </select>
		           </td>
	            </tr>
				
				<!-- Create table row for Create Item button -->
				<tr>
					<td>
	            		<button type="submit">Create Item</button>
	            	</td>
	            </tr>
	            </table>
	        </form>
	
	        <%-- Processing form data if the request is a POST --%>
	        <%-- 
	        TODO:
	        implement logic to retrieve the email of the retailer that is logged in (sessions???)
	        use the FoodItemsDAOImpl addFoodItem method to add the food item to the database
	        --%>
	        <%
	            if ("POST".equalsIgnoreCase(request.getMethod())) {
					// store the itemName from the form in a variable
	                String itemName = request.getParameter("itemName");

					// store the quantity from the form in a variable
	                int quantity = Integer.parseInt(request.getParameter("quantity"));

					// store the expiration date from the form in a variable
	                Date expirationDate = Utility.parseDate(request.getParameter("expirationDate"));

					// store the price from the form in a variable
	                double price = Double.parseDouble(request.getParameter("price"));

					// store the surplus from the form in a variable
	                boolean surplus = Boolean.parseBoolean(request.getParameter("surplus"));

					// store the listing type from the form in a variable
	                String listingType = request.getParameter("listingType");

					// store the discount price from the form in a variable
	                String discountPrice = request.getParameter("discountPrice");

					// store the donation price from the form in a variable
	                String retailerEmail = (String) session.getAttribute("username");

					// create a boolean variable to store the validity of the input
	                boolean isValid = true;

	                // Validate the input
	                if (!surplus && !"regular".equalsIgnoreCase(listingType)) {
	                    isValid = false;
	                    out.print("<p style='color:red;'>Invalid Input: Only surplus items can be discount or donation.</p>");
	                }

	                // If valid, process the form
	                if (isValid) {
	                    FoodItemsDAOImpl dao = new FoodItemsDAOImpl();
		                
	                //implement error checking to ensure negative numbers arent entered as quantity
	                if (quantity < 0){
	                	out.println("<p>Please enter quantity greater than zero.</p>");
	                }else{
					// add the item to the database using the addFoodItem method
	                dao.addFoodItem(itemName, expirationDate, quantity, price, surplus, listingType, retailerEmail);
		                response.sendRedirect("retailer.jsp");
	                } 
	                }
	            }
	        %>
		</div>
    </body>
</html>
