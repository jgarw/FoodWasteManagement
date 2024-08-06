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
        </table>
        <div>
        	<button type="submit" class="btn">Login</button>
        	<a href="register.jsp"><button type="button" class="btn">Register</button></a>
        </div>
    </form>

	<!-- Java code to handle user authorization and redirection to correct page -->
    <% 
        if ("post".equalsIgnoreCase(request.getMethod())) {
            String email = request.getParameter("username");
            String password = request.getParameter("password");

            if (email != null && password != null) {
                UserDAOImpl userDAO = new UserDAOImpl();
                User user = userDAO.authUser(email, password);

                if (user != null) {
                    session.setAttribute("name", user.getName());
                    session.setAttribute("username", user.getEmail());
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
                    out.println("<p>Invalid email or password. Please try again.</p>");
                }
            } else {
                out.println("<p>Please enter email and password</p>");
            }
        }
    %>
</div>

<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>