<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Registration</h2>

<div>
    <FORM ACTION="RegisterServlet" METHOD="POST">
        <table>
            <tr>
                <td>Name:</td>
                <td><INPUT TYPE="TEXT" NAME="name" VALUE="" placeholder="First and last name"></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><INPUT TYPE="TEXT" NAME="email" VALUE="" placeholder="sample123@gmail.com"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><INPUT TYPE="password" NAME="password" VALUE="" ></td>
            </tr>
            <tr>
                <td>Confirmed Password:</td>
                <td><INPUT TYPE="password" NAME="confirmedPassword" VALUE=""></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><INPUT TYPE="TEXT" NAME="phone" VALUE="" placeholder="e.g. 6135559999"></td>
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
                <td><INPUT TYPE="checkbox" NAME="Subscribe"> Subscribe</td>
            </tr>
            <tr>
                <td></td>
                <td><button>Submit</button>&nbsp;<a href="index.jsp"><button>Go Back</button></a></td>
            </tr>
        </table>
    </FORM>
</div>
</body>
</html>