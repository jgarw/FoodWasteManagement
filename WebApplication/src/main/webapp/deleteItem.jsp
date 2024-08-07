<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
FoodItemsDAOImpl dao = new FoodItemsDAOImpl();
String itemId = request.getParameter("id");
dao.deleteFoodItem(Integer.parseInt(itemId));
response.sendRedirect("retailer.jsp");
%>