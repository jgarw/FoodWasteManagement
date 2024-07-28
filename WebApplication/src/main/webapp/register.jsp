<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">
    <h2>Registration</h2>
    <form action="RegisterServlet" method="POST">
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
                <td><input type="checkbox" name="Subscribe"> Subscribe</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit" class="btn">Submit</button>
                    <a href="index.jsp"><button type="button" class="btn">Back to Main</button></a>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
