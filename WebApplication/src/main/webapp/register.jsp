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
<div class="container">
    <h2>Registration</h2>
    <form action="register.jsp" method="POST">
        <table>
            <tr>
                <td>Name:</td>
                <td><input type="text" name="name" placeholder="First and last name"></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="text" name="email" placeholder="sample123@gmail.com"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td>Confirmed Password:</td>
                <td><input type="password" name="confirmedPassword"></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><input type="text" name="phone" placeholder="e.g. 6135559999 no spaces"></td>
            </tr>
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
            <tr>
                <td></td>
                <td><input type="checkbox" name="subscribe"> Subscribe to Food Alerts</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit" class="btn">Submit</button>
                    <a href="index.jsp"><button type="button" class="btn">Go Back</button></a>
                </td>
            </tr>
        </table>
    </form>

    <%
if ("post".equalsIgnoreCase(request.getMethod())) {
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String password = null;
    String phone = request.getParameter("phone");
    String userType = request.getParameter("userType");
    boolean subscribed = request.getParameter("subscribe") != null;

    if (request.getParameter("password").equals(request.getParameter("confirmedPassword"))) {
        password = request.getParameter("password");
    }

    if (password != null) {
        UserDAOImpl userDAO = new UserDAOImpl();
        User user = UserFactory.createUser(userType, name, email, password, phone);
        userDAO.insertUser(user);

        if(subscribed) {
            SubscriberDAOImpl subscriberDAO = new SubscriberDAOImpl();
            subscriberDAO.subscribeToAlerts(email);
        }

        response.sendRedirect("login.jsp");

    } else {
        // Handle password mismatch error
        out.println("Password and confirmed password do not match.");
    }
}
%>
</div>
</body>
</html>