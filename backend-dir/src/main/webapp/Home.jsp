<%-- 
    Document   : home.jsp
    Created on : Mar 1, 2025, 9:32:07 AM
    Author     : Asus
--%>

<%@page import="Common.Constant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">QNITY</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <%if (request.getSession().getAttribute(Constant.KEY_AUTH) == null) {%>
                        <li class="nav-item">
                            <a class="btn btn-primary me-2" href="./login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-success" href="./signup">Sign Up</a>
                        </li>
                        <%} else {%>     
                   
                        <li class="nav-item">
                            <a class="btn btn-success" href="./logout">Log out</a>
                        </li>
                        <%}%>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container text-center mt-5">
            <h1>Welcome to QNITY - Your Quy Nhon Travel Explorer Assistant</h1>
            <p class="lead">Discover Quy Nhon and plan your perfect trip!</p>
            <% if (request.getAttribute("error") != null) {%>
            <div class="alert alert-danger"><%= request.getAttribute("error")%></div>
            <% }%>
            <%if (request.getSession().getAttribute(Constant.KEY_AUTH) != null) {%>
            <a class="btn btn-lg btn-warning" href="./tripplanner">Plan your trip</a>
            <a class="btn btn-lg btn-warning" href="./tripitinerary">View your itinerary</a>
            <%}%>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

