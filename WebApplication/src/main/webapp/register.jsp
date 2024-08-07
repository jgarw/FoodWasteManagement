<%@ page import="java.sql.*" %>
<%@ page import="com.cst8288.finalproject.controller.*" %>
<%@ page import="com.cst8288.finalproject.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<!-- place all items in container -->
<div class="container">
    <h2>Registration</h2>
    <!-- create a form to allow the user to register -->
    <form action="register.jsp" method="POST">
        <table>
            <!-- create table row for user to enter name-->
            <tr>
                <td>Name:</td>
                <td><input type="text" name="name" placeholder="First and last name"></td>
            </tr>
            <!-- create table row for user to enter email-->
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email" placeholder="sample123@gmail.com"></td>
            </tr>
            <!-- create table row for user to enter password-->
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password"></td>
            </tr>
            <!-- create table row for user to confirm password-->
            <tr>
                <td>Confirmed Password:</td>
                <td><input type="password" name="confirmedPassword"></td>
            </tr>
            <!-- create table row for user to enter phone number-->
            <tr>
                <td>Phone:</td>
                <td><input type="text" name="phone" placeholder="e.g. 6135559999 no spaces"></td>
            </tr>
            <!-- create table row for user to select account type-->
            <tr>
                <td>User Type:</td>
                <td>
                    <select name="userType" required>
                        <option selected disabled>Account Type</option>
                        <option value="Retailer">Retailer</option>
                        <option value="Organization">Charitable organization</option>
                        <option value="Consumer">Consumer</option>
                    </select>
                </td>
            </tr>
            <!-- create table row for user to subscribe to food alerts-->
            <tr>
                <td></td>
                <td><input type="checkbox" name="subscribe"> Subscribe to Food Alerts</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit" class="btn">Submit</button>
                    <a href="login.jsp"><button type="button" class="btn">Login Page</button></a>
                </td>
            </tr>
        </table>
    </form>

    <%
if ("post".equalsIgnoreCase(request.getMethod())) {

    // Retrieve name from the request and store it in a variable
    String name = request.getParameter("name");

    // Retrieve email from the request and store it in a variable
    String email = request.getParameter("email");

    // Retrieve phone from the request and store it in a variable
    String password = null;

    // Retrieve phone from the request and store it in a variable
    String phone = request.getParameter("phone");

    // Retrieve userType from the request and store it in a variable
    String userType = request.getParameter("userType");

    // Retrieve subscribe from the request and store it in a variable
    boolean subscribed = request.getParameter("subscribe") != null;

    // Check if password and confirmed password match
    if (request.getParameter("password").equals(request.getParameter("confirmedPassword"))) {
        password = request.getParameter("password");
    }

    // Check if email and password are not null
    if (password != null) {
        
        UserDAOImpl userDAO = new UserDAOImpl();

        // Create a new user object using the factory method
        User user = UserFactory.createUser(userType, name, email, password, phone);

        // Insert the user into the database
        userDAO.insertUser(user);

        // Subscribe to alerts if the user has checked the subscribe checkbox
        if(subscribed) {
            SubscriberDAOImpl subscriberDAO = new SubscriberDAOImpl();
            subscriberDAO.subscribeToAlerts(email);
        }

        // Redirect to the login page
        response.sendRedirect("login.jsp");

    } else {
        // Handle password mismatch error
        out.println("Password and confirmed password do not match.");
    }
}
%>
</div>

    <!-- include pre-made footer file-->
    <footer>
        <p>&copy; 2024 WeHateFoodWaste. All rights reserved.</p>
    </footer>
</body>
</html>
