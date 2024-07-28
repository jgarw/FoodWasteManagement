<%@ page import="java.sql.*" %>
<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Login</title>
</head>
<body>
<div class="container">
    <h2>Login</h2>
    <form method="post" action="login.jsp">
        <table>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td><button type="submit" class="btn">Login</button></td>
                <td><a href="register.jsp"><button type="button" class="btn">Register</button></a></td>
            </tr>
        </table>
    </form>

	<!-- Java code to handle user authorization and redirection to correct page -->
    <%
        if ("post".equalsIgnoreCase(request.getMethod())) {
            String email = request.getParameter("username");
            String password = request.getParameter("password");
            UserDAOImpl userDAO = new UserDAOImpl();

            User user = userDAO.authUser(email, password);

            
                if (user instanceof Retailer) {
                    response.sendRedirect("retailer.jsp");
                } else if (user instanceof Consumer) {
                    response.sendRedirect("consumer.jsp");
                } else if (user instanceof Organization) {
                    response.sendRedirect("organization.jsp");
                } else {
                    out.println("<p>Invalid user type!</p>");
                }
            } else {
                out.println("<p>Please enter email and password</p>");
            }
        
    %>
</div>
</body>
</html>
