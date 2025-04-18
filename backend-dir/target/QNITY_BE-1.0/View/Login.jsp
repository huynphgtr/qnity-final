<%-- 
    Document   : Login
    Created on : Feb 25, 2025, 7:25:58 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log in</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="./">QNITY</a>
            </div>
        </nav>

        <div class="container mt-5">
            <h2 class="text-center">Log in</h2>
            <form class="w-50 mx-auto mt-4" method="post" action="${pageContext.request.contextPath}/login">
                <% if (request.getAttribute("error") != null) {%>
                <div class="alert alert-danger"><%= request.getAttribute("error")%></div>
                <% }%>
                <div class="mb-3">
                    <label for="email" class="form-label">Email address</label>
                    <input type="email" class="form-control" name="email" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" required>
                </div>
                <button type="submit" class="btn btn-success w-100">Log in</button>
            </form>
            <div class="text-center mt-3">
                <a href="./signup">Not have account yet?</a>
            </div>
        </div>

    </body>
</html>
