<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- This page is used to handle the logic for deleting FoodItems from the FoodItems table as a Retailer -->
<%
    // instantiate a FoodItemsDAOImpl object
    FoodItemsDAOImpl dao = new FoodItemsDAOImpl();

    // get the id of the item to be deleted from the request
    String itemId = request.getParameter("id");

    // call the deleteFoodItem method from the FoodItemsDAOImpl object to delete the item from the database
    dao.deleteFoodItem(Integer.parseInt(itemId));

    // redirect the user back to the retailer.jsp page
    response.sendRedirect("retailer.jsp");
%>