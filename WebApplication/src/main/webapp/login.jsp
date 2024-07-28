<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Login</h2>
    <form action="LoginServlet" method="POST">
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
</div>
</body>
</html>
