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
<!-- add all items to container-->
<div class="container">
    <h2>Login</h2>
    <form method="post" action="login.jsp">
        <!-- create table to hold input fields for logging in -->
        <table>
            <!-- create input field for email -->
            <tr>
                <td>Email:</td>
                <td><input type="text" name="username"></td>
            </tr>
            <!-- create input field for password -->
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password"></td>
            </tr>
        </table>
        <!-- create buttons for logging in and registering -->
        <div>
        	<button type="submit" class="btn">Login</button>
        	<a href="register.jsp"><button type="button" class="btn">Register</button></a>
        </div>
    </form>

	<!-- Java code to handle user authorization and redirection to correct page -->
    <% 
        if ("post".equalsIgnoreCase(request.getMethod())) {
            // get the email from the request and store it in a variable
            String email = request.getParameter("username");

            // get the password from the request and store it in a variable
            String password = request.getParameter("password");

            // check if the email and password are not null
            if (email != null && password != null) {
                // instantiate a UserDAOImpl object
                UserDAOImpl userDAO = new UserDAOImpl();

                // call the authUser method from the UserDAOImpl object to authenticate the user and store the result in a User variable
                User user = userDAO.authUser(email, password);

                // check if the user is not null
                if (user != null) {
                    session.setAttribute("name", user.getName());
                    session.setAttribute("username", user.getEmail());

                    // check if the user is an instance of Retailer and redirect to the retailer.jsp page
                    if (user instanceof Retailer) {
                        response.sendRedirect("retailer.jsp");
                    // check if the user is an instance of Consumer and redirect to the consumer.jsp page
                    } else if (user instanceof Consumer) {
                        response.sendRedirect("consumer.jsp");
                    // check if the user is an instance of Organization and redirect to the organization.jsp page
                    } else if (user instanceof Organization) {
                        response.sendRedirect("organization.jsp");
                    // if user is not an instance of any of the above, print an error message
                    } else {
                        out.println("<p>Invalid user type!</p>");
                    }
                } 
                // if the user is null, print an error message. likely wrong password or email
                else {
                    out.println("<p>Invalid email or password. Please try again.</p>");
                }
            } else {
                out.println("<p>Please enter email and password</p>");
            }
        }
    %>
</div>

<!-- add footer to the page -->
<footer>
    <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
</footer>
</body>
</html>