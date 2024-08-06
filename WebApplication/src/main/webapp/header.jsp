<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="header" style="display: flex; justify-content: space-between; align-items: center; padding: 1px 1px; background-color: rgba(255, 255, 255, 0.9); box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);">
    <h1 class="header-title">WeHateFoodWaste</h1>
    <div class="header-right">
        <% 
            String backUrl = (String) request.getAttribute("backUrl");
            if (backUrl != null) { 
        %>
            <a href="<%= backUrl %>"><button type="button">Back</button></a>
        <% 
            } 
        %>
        <form action="logout.jsp" method="post">
            <button type="submit">Logout</button>
        </form>
    </div>
</div>

</body>
</html>
