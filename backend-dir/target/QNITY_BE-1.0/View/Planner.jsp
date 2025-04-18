<%-- 
    Document   : Planner
    Created on : Feb 28, 2025, 11:15:29 PM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Planner</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container-fluid">
                <a class="navbar-brand" href="./Home.jsp">QNITY</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>               
            </div>
        </nav>
        <div class="container mt-5">
            <h2 class="text-center mb-4">Plan Your Trip</h2>
            <form class="w-50 mx-auto mt-4" method="post">
                <div class="mb-3">
                    <label for="startDate" class="form-label">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" required>
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">End Date</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" required>
                </div>
                <div class="mb-3">
                    <label for="budget" class="form-label">Budget (VND)</label>
                    <input type="number" class="form-control" id="budget" name="budget" placeholder="Enter your budget" required>
                </div>
                <div class="mb-3">
                    <label for="numPeople" class="form-label">Number of People</label>
                    <input type="number" class="form-control" id="numPeople" name="numPeople" placeholder="Enter number of people" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" name="interest">Interests</label>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="food">
                        <label class="form-check-label" for="food">Food</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="nature">
                        <label class="form-check-label" for="nature">Nature</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="culture">
                        <label class="form-check-label" for="culture">Culture</label>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary w-100" href="./tripitinerary">Create Itinerary</button>
            </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
